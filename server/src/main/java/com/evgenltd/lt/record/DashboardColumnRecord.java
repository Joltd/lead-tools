package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.DashboardColumn;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record DashboardColumnRecord(Long id, AttributeRecord attribute, DashboardColumn.Order order, Integer position, Integer width) {

    public static DashboardColumnRecord from(final DashboardColumn dashboardColumn) {
        return new DashboardColumnRecord(
                dashboardColumn.getId(),
                AttributeRecord.fromSimple(dashboardColumn.getAttribute()),
                dashboardColumn.getOrder(),
                dashboardColumn.getPosition(),
                dashboardColumn.getWidth()
        );
    }

    public DashboardColumn toEntity() {
        final DashboardColumn dashboardColumn = new DashboardColumn();
        dashboardColumn.setId(id());
        dashboardColumn.setAttribute(attribute().toEntity());
        dashboardColumn.setOrder(order());
        dashboardColumn.setPosition(position());
        dashboardColumn.setWidth(width());
        return dashboardColumn;
    }

}
