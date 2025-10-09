package com.example.userauthservice_june2025.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends BaseModel {
    private String token;

    @ManyToOne
    private User user;

    private SessionState state;
}
