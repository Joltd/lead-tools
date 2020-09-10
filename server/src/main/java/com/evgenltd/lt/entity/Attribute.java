package com.evgenltd.lt.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "attribute", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AttributeColor> colors = new HashSet<>();

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

    public Set<AttributeColor> getColors() {
        return colors;
    }

    public void setColors(final Set<AttributeColor> colors) {
        this.colors = colors;
    }
}
