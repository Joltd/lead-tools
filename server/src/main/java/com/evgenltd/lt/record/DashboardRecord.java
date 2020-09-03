package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Dashboard;
import com.evgenltd.lt.entity.DashboardColumn;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final record DashboardRecord(Long id, String name, String query, List<DashboardColumnRecord> columns) {

    public static DashboardRecord from(final Dashboard dashboard) {
        return new DashboardRecord(
                dashboard.getId(),
                dashboard.getName(),
                dashboard.getQuery(),
                dashboard.getColumns()
                        .stream()
                        .map(DashboardColumnRecord::from)
                        .sorted(Comparator.comparing(DashboardColumnRecord::position))
                        .collect(Collectors.toList())
        );
    }

    public Dashboard toEntity() {
        final Dashboard dashboard = new Dashboard();
        dashboard.setId(id());
        dashboard.setName(name());
        dashboard.setQuery(query());
        for (final DashboardColumnRecord dashboardColumnRecord : columns()) {
            final DashboardColumn dashboardColumn = dashboardColumnRecord.toEntity();
            dashboardColumn.setDashboard(dashboard);
            dashboard.getColumns().add(dashboardColumn);
        }
        return dashboard;
    }

}
