package com.api.entities.residence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "countries")
public class Country {
    @Id
    private Integer id;

    @Column(value = "name")
    private String name;

    @Column(value = "phone_preffix")
    private Short phonePreffix;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getPhonePreffix() {
        return phonePreffix;
    }

    public void setPhonePreffix(Short phonePreffix) {
        this.phonePreffix = phonePreffix;
    }
}
