package com.evgenltd.lt.entity;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String number;

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
}
