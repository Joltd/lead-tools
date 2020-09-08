package com.evgenltd.lt.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TicketAttribute> attributes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Set<TicketAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(final Set<TicketAttribute> attributes) {
        this.attributes = attributes;
    }

}
