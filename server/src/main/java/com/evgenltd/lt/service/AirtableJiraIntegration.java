package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.User;
import com.evgenltd.lt.component.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class AirtableJiraIntegration {

    private final JiraRestClient jira;

    @Value("${airtable.host}")
    private String airtableHost;

    @Value("${airtable.apiKey}")
    private String airtableApikey;

    private WebClient airtable;

    public AirtableJiraIntegration(final JiraRestClient jira) {
        this.jira = jira;
    }

    @PostConstruct
    public void postConstruct() {
        this.airtable = WebClient.builder()
                .baseUrl(airtableHost)
                .defaultHeader("Authorization", "Bearer " + airtableApikey)
                .build();
    }

//    @Scheduled(cron = "0 0 10-20 * * *")
//    public void scheduled() {
//        load();
//    }

    public void load(final String number) {

        final Body body = airtable.get()
                .uri(uriBuilder -> uriBuilder.path("/Tickets/").queryParam("fields[]", "Number").build())
                .retrieve()
                .bodyToMono(Body.class)
                .block();

        for (final Ticket ticket : body.getRecords()) {
            if (Utils.isBlank(number) || Objects.equals(ticket.getFields().getOrDefault("Number", ""), number)) {
                refresh(ticket);
            }
        }

    }

    private void refresh(final Ticket ticket) {

        final Issue issue = jira.getIssueClient()
                .getIssue(ticket.getFields().get("Number").toString())
                .recover(error -> null)
                .claim();

        ticket.getFields().put("Summary", issue.getSummary());
        ticket.getFields().put("Assignee", Optional.ofNullable(issue.getAssignee()).map(User::getDisplayName).orElse(null));
        ticket.getFields().put("Status", issue.getStatus().getName());
        ticket.getFields().put("Story Points", getFieldValue(issue.getFields(), "Story Points"));
        ticket.getFields().put("Resolve Expected On", getFieldValue(issue.getFields(), "Resolve Expected On"));

        update(ticket);

    }

    private Object getFieldValue(final Iterable<IssueField> fields, final String fieldName) {
        return StreamSupport.stream(fields.spliterator(), false)
                .filter(field -> Objects.equals(field.getName(), fieldName))
                .findFirst()
                .map(IssueField::getValue)
                .orElse(null);
    }

    private void update(final Ticket ticket) {

        final Body body = new Body();
        body.getRecords().add(ticket);

        final ResponseEntity<Void> response = airtable.patch()
                .uri("/Tickets")
                .body(Mono.just(body), Body.class)
                .retrieve()
                .toBodilessEntity()
                .onErrorStop()
                .block(Duration.ofSeconds(1L));

        System.out.println(response);
    }

    public static final class Body {
        private List<Ticket> records = new ArrayList<>();

        public List<Ticket> getRecords() {
            return records;
        }

        public void setRecords(final List<Ticket> records) {
            this.records = records;
        }
    }

    public static final class Ticket {
        private String id;
        private Map<String, Object> fields;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }

        public Map<String, Object> getFields() {
            return fields;
        }

        public void setFields(final Map<String, Object> fields) {
            this.fields = fields;
        }
    }

}
