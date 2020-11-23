package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JiraService {

    private final JiraRestClient client;

    public JiraService(
            final JiraRestClient client
    ) {
        this.client = client;
    }

    public List<Issue> load(final String query) {

        int total = 1;
        final List<Issue> result = new ArrayList<>();

        for (int index = 0; index < total; index = index + 50) {
            final SearchResult claim = client.getSearchClient()
                    .searchJql(query, 50, index, Collections.emptySet())
                    .claim();

            for (final Issue issue : claim.getIssues()) {
                result.add(issue);
            }

            total = claim.getTotal();

            if (!claim.getIssues().iterator().hasNext()) {
                break;
            }
        }

        return result;

    }

    public Issue loadByNumber(final String number) {
        return client.getIssueClient()
                .getIssue(number)
                .recover(error -> null)
                .claim();
    }

}
