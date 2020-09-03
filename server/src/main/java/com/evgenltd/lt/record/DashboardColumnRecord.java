package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Dashboard;
import com.evgenltd.lt.entity.DashboardColumn;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Comparator;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record DashboardColumnRecord(Long id, Attribute attribute, DashboardColumn.Order order, Integer position, Integer width) {

    public static DashboardColumnRecord from(final DashboardColumn dashboardColumn) {
        return new DashboardColumnRecord(
                dashboardColumn.getId(),
                dashboardColumn.getAttribute(),
                dashboardColumn.getOrder(),
                dashboardColumn.getPosition(),
                dashboardColumn.getWidth()
        );
    }

    public DashboardColumn toEntity() {
        final DashboardColumn dashboardColumn = new DashboardColumn();
        dashboardColumn.setId(id());
        dashboardColumn.setAttribute(attribute());
        dashboardColumn.setOrder(order());
        dashboardColumn.setPosition(position());
        dashboardColumn.setWidth(width());
        return dashboardColumn;
    }

}
