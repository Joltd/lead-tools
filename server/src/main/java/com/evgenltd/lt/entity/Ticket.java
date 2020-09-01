package com.evgenltd.lt.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String number;

    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<TicketAttribute> attributes = new HashSet<>();

    private String comment;

    private Boolean tracked = false;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public Boolean getTracked() {
        return tracked;
    }

    public void setTracked(final Boolean tracked) {
        this.tracked = tracked;
    }

    public Set<TicketAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(final Set<TicketAttribute> attributes) {
        this.attributes = attributes;
    }
}
