package com.api.entities.residence;

import com.api.entities.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "countries")
@Data
public class Country {
    @Id
    private Integer id;

    @Column(value = "name")
    private String name;

    @Column(value = "phone_preffix")
    private Short phonePreffix;

    @Transient
    private List<User> users;
}
