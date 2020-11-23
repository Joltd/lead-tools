package com.evgenltd.lt.service;

import com.evgenltd.lt.component.Utils;
import com.evgenltd.lt.record.AirtableBody;
import com.evgenltd.lt.record.AirtableRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class AirtableService {

    private static final Logger log = LoggerFactory.getLogger(AirtableService.class);

    @Value("${airtable.host}")
    private String airtableHost;

    @Value("${airtable.apiKey}")
    private String airtableApikey;

    private WebClient airtable;

    private final ObjectMapper objectMapper;

    public AirtableService(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void postConstruct() {
        this.airtable = WebClient.builder()
                .baseUrl(airtableHost)
                .defaultHeader("Authorization", "Bearer " + airtableApikey)
                .build();
    }

    public List<AirtableRecord.Existed> load(final String path, final String... fields) {

        final List<AirtableRecord.Existed> result = new ArrayList<>();
        String offset = null;

        while (true) {

            final AirtableBody.Response body = airtable.get()
                    .uri(prepareUriBuilder(path, offset, fields))
                    .retrieve()
                    .onStatus(
                            status -> !Objects.equals(status, HttpStatus.OK),
                            clientResponse -> Mono.empty()
                    )
                    .bodyToMono(AirtableBody.Response.class)
                    .block();

            if (body == null) {
                break;
            }

            result.addAll(body.getRecords());

            if (body.getRecords().isEmpty() || Utils.isBlank(body.getOffset())) {
                break;
            }

            offset = body.getOffset();

        }

        return result;

    }

    private Function<UriBuilder, URI> prepareUriBuilder(final String path, final String offset, final String... fields) {
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (Utils.isNotBlank(offset)) {
            queryParams.add("offset", offset);
        }
        for (final String field : fields) {
            queryParams.add("fields[]", field);
        }
        return uriBuilder -> uriBuilder.path(path).queryParams(queryParams).build();
    }

    public void createOrUpdate(final String path, final AirtableRecord record) {
        if (record instanceof AirtableRecord.Existed) {
            update(path, record);
        } else {
            create(path, record);
        }
    }

    private void update(final String path, final AirtableRecord record) {
        final AirtableBody.Request body = new AirtableBody.Request();
        body.getRecords().add(record);

        airtable.patch()
                .uri(path)
                .body(Mono.just(body), AirtableBody.Request.class)
                .retrieve()
                .onStatus(
                        httpStatus -> !Objects.equals(httpStatus, HttpStatus.OK),
                        clientResponse -> Mono.empty()
                )
                .bodyToMono(AirtableBody.Response.class)
                .block();
    }

    private void create(final String path, final AirtableRecord record) {
        final AirtableBody.Request body = new AirtableBody.Request();
        body.getRecords().add(record);

        airtable.post()
                .uri(path)
                .body(Mono.just(body), AirtableBody.Request.class)
                .header("Content-Type", "application/json")
                .retrieve()
                .onStatus(
                        httpStatus -> !Objects.equals(httpStatus, HttpStatus.OK),
                        clientResponse -> Mono.empty()
                )
                .bodyToMono(AirtableBody.Request.class)
                .block();
    }

    public void delete(final String path, final AirtableRecord.Existed record) {
        airtable.delete()
                .uri(uriBuilder -> uriBuilder.path(path).queryParam("records[]", record.getId()).build())
                .retrieve()
                .onStatus(
                        httpStatus -> !Objects.equals(httpStatus, HttpStatus.OK),
                        clientResponse -> Mono.empty()
                )
                .bodyToMono(AirtableBody.class)
                .block();
    }

}
