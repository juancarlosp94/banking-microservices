package com.example.account_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CustomerReference {

    @Id
    private Long id;

    private String name;
    private Boolean active;

    public Long getId() { return id; }
    public String getName() { return name; }
    public Boolean getActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setActive(Boolean active) { this.active = active; }
}