package com.evgenltd.lt.controller;


import com.evgenltd.lt.entity.Dashboard;
import com.evgenltd.lt.record.DashboardRecord;
import com.evgenltd.lt.repository.DashboardRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardRepository dashboardRepository;

    public DashboardController(final DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @GetMapping
    public Response<List<DashboardRecord>> list() {
        final List<DashboardRecord> result = dashboardRepository.findAll(Sort.by("name"))
                .stream()
                .map(DashboardRecord::from)
                .collect(Collectors.toList());
        return new Response<>(result);
    }

    @GetMapping("/{id}")
    public Response<DashboardRecord> loadById(@PathVariable("id") final Long id) {
        return new Response<>(DashboardRecord.from(dashboardRepository.getOne(id)));
    }

    @PostMapping
    public Response<DashboardRecord> update(@RequestBody final DashboardRecord dashboardRecord) {
        final Dashboard dashboard = dashboardRecord.toEntity();
        final Dashboard result = dashboardRepository.save(dashboard);
        return new Response<>(DashboardRecord.from(result));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable("id") final Long id) {
        dashboardRepository.deleteById(id);
        return new Response<>();
    }

}
