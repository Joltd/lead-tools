package com.evgenltd.lt.entity;

import javax.persistence.*;

@Entity
@Table(name = "attributes")
public class Attribute {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Boolean readonly = false;

    private String link;

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

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public Boolean getReadonly() {
        return readonly;
    }

    public void setReadonly(final Boolean readonly) {
        this.readonly = readonly;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public enum Type {
        STRING,
        TEXT,
        NUMBER,
        DATE,
        DATETIME,
        BOOLEAN
    }
}
