package com.digdes.simple.model;

import jakarta.persistence.*;

@Entity
@Table(name = "simple")
public class SimpleModel {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;

    public SimpleModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ("{"+ id +"; " + name + "}");
    }
}
