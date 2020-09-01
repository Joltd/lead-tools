package com.evgenltd.lt.controller;


import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Dashboard;
import com.evgenltd.lt.repository.AttributeRepository;
import com.evgenltd.lt.repository.DashboardRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardRepository dashboardRepository;
    private final AttributeRepository attributeRepository;

    public DashboardController(
            final DashboardRepository dashboardRepository,
            final AttributeRepository attributeRepository
    ) {
        this.dashboardRepository = dashboardRepository;
        this.attributeRepository = attributeRepository;
    }

    @GetMapping
    public Response<List<DashboardRecord>> list() {
        final List<DashboardRecord> result = dashboardRepository.findAll(Sort.by("name"))
                .stream()
                .map(this::toRecord)
                .collect(Collectors.toList());
        return new Response<>(result);
    }

    @PostMapping
    public Response<DashboardRecord> update(@RequestBody final DashboardRecord dashboardRecord) {
        final Dashboard dashboard = toEntity(dashboardRecord);
        final Dashboard result = dashboardRepository.save(dashboard);
        return new Response<>(toRecord(result));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable("id") final Long id) {
        dashboardRepository.deleteById(id);
        return new Response<>();
    }

    private DashboardRecord toRecord(final Dashboard dashboard) {
        return new DashboardRecord(
                dashboard.getId(),
                dashboard.getName(),
                dashboard.getQuery(),
                dashboard.getColumns()
                        .stream()
                        .map(Attribute::getId)
                        .collect(Collectors.toList())
        );
    }

    private Dashboard toEntity(final DashboardRecord record) {
        final Dashboard dashboard = new Dashboard();
        dashboard.setId(record.id());
        dashboard.setName(record.name());
        dashboard.setQuery(record.query());
        final List<Attribute> attributes = attributeRepository.findAllById(record.attributes());
        dashboard.getColumns().addAll(attributes);
        return dashboard;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static final record DashboardRecord(Long id, String name, String query, List<Long> attributes) {}

}
