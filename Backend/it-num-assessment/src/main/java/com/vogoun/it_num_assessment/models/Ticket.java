package com.vogoun.it_num_assessment.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Ticket {
    private @Id
    @GeneratedValue Long id;

    private String message;

    Ticket() {}

    public Long getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
