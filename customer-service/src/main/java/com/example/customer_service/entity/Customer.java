package com.example.customer_service.entity;

import jakarta.persistence.Entity;

@Entity
public class Customer extends Person {

    private String password;
    private Boolean active;

    public String getPassword() { return password; }
    public Boolean getActive() { return active; }

    public void setPassword(String password) { this.password = password; }
    public void setActive(Boolean active) { this.active = active; }
}