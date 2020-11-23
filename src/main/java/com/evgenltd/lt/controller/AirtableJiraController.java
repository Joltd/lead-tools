package com.evgenltd.lt.controller;

import com.evgenltd.lt.service.AirtableJiraIntegration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/airtable-jira")
public class AirtableJiraController {

    private final AirtableJiraIntegration integration;

    public AirtableJiraController(final AirtableJiraIntegration integration) {
        this.integration = integration;
    }

    @GetMapping("/syncByQuery/{query}")
    public void syncByQuery(@PathVariable("query") final String query) {
        integration.syncByQuery(query);
    }

    @GetMapping("/addByBatch/{numbers}")
    public void addByBatch(@PathVariable("numbers") final String numbers) {
        integration.addByBatch(numbers);
    }

    @GetMapping("/refreshAll")
    public void refreshAll() {
        integration.refreshAll();
    }

}
