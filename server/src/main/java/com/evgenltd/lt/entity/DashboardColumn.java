package com.evgenltd.lt.entity;

import javax.persistence.*;

@Entity
@Table(name = "dashboard_columns")
public class DashboardColumn {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @Enumerated(EnumType.STRING)
    private Order order;

    private Integer position;

    private Integer width;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(final Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(final Attribute attribute) {
        this.attribute = attribute;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final Integer position) {
        this.position = position;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(final Integer width) {
        this.width = width;
    }

    public enum Order {
        NONE,
        ASC,
        DESC
    }

}
