package com.evgenltd.lt;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClient;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Application extends SpringBootServletInitializer {

    @Value("${jira.host}")
    private String jiraHost;

    @Value("${jira.user}")
    private String jiraUser;

    @Value("${jira.password}")
    private String jiraPassword;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }

    @Bean(destroyMethod = "destroy")
    public DisposableHttpClient jiraHttpClient() {
        return new AsynchronousHttpClientFactory().createClient(
                URI.create(jiraHost),
                new BasicHttpAuthenticationHandler(jiraUser, jiraPassword)
        );
    }

    @Bean
    public JiraRestClient jiraRestClient(final DisposableHttpClient httpClient) {
        return new AsynchronousJiraRestClient(URI.create(jiraHost), httpClient);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}
