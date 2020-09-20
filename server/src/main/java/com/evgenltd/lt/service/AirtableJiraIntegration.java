package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.User;
import com.evgenltd.lt.component.Utils;
import com.evgenltd.lt.record.AirtableBody;
import com.evgenltd.lt.record.AirtableRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class AirtableJiraIntegration {

    private final JiraService jiraService;

    private final AirtableService airtableService;

    public AirtableJiraIntegration(
            final JiraService jiraService,
            final AirtableService airtableService
    ) {
        this.jiraService = jiraService;
        this.airtableService = airtableService;
    }

    public void syncByQuery(final String query) {
        final List<Issue> issues = jiraService.load(query);
        final Map<String, AirtableRecord> ticketIndex = airtableService.load("/Tickets/", "Number")
                .stream()
                .collect(Collectors.toMap(ticket -> ticket.get("Number").toString(), ticket -> ticket, (left,right) -> left));

        for (final Issue issue : issues) {
            final AirtableRecord airtableRecord = ticketIndex.computeIfAbsent(issue.getKey(), (key) -> new AirtableRecord());
            refreshAirtableWithJira(airtableRecord, issue);
        }
    }

    public void addByBatch(final String numbers) {
        final Map<String, AirtableRecord> ticketIndex = airtableService.load("/Tickets/", "Number")
                .stream()
                .collect(Collectors.toMap(ticket -> ticket.get("Number").toString(), ticket -> ticket, (left,right) -> left));

        for (final String number : numbers.split(",")) {
            final Issue issue = jiraService.loadByNumber(number);

            final AirtableRecord airtableRecord = ticketIndex.computeIfAbsent(issue.getKey(), (key) -> new AirtableRecord());
            refreshAirtableWithJira(airtableRecord, issue);
        }
    }

    public void refreshAll() {
        final List<? extends AirtableRecord> airtableRecords = airtableService.load("/Tickets/", "Number");
        for (final AirtableRecord airtableRecord : airtableRecords) {
            final Issue issue = jiraService.loadByNumber(airtableRecord.get("Number").toString());
            refreshAirtableWithJira(airtableRecord, issue);
        }
    }

    private void refreshAirtableWithJira(final AirtableRecord airtableRecord, final Issue issue) {
        airtableRecord.set("Number", issue.getKey());
        airtableRecord.set("Summary", issue.getSummary());
        airtableRecord.set("Assignee", Optional.ofNullable(issue.getAssignee()).map(User::getDisplayName).orElse(null));
        airtableRecord.set("Status", issue.getStatus().getName());
        airtableRecord.set("Story Points", getFieldValue(issue.getFields(), "Story Points"));
        airtableRecord.set("Resolve Expected On", getFieldValue(issue.getFields(), "Resolve Expected On"));

        airtableService.createOrUpdate("/Tickets", airtableRecord);
    }


    private Object getFieldValue(final Iterable<IssueField> fields, final String fieldName) {
        return StreamSupport.stream(fields.spliterator(), false)
                .filter(field -> Objects.equals(field.getName(), fieldName))
                .findFirst()
                .map(IssueField::getValue)
                .orElse(null);
    }

}
