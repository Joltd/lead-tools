package com.evgenltd.lt.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dashboards")
public class Dashboard {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    private String query;

    @OneToMany(mappedBy = "dashboard", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<DashboardColumn> columns = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public Set<DashboardColumn> getColumns() {
        return columns;
    }

    public void setColumns(final Set<DashboardColumn> columns) {
        this.columns = columns;
    }
}
