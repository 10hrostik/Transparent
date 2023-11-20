package com.api.entities.chat;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
public abstract class Room {
    @Id
    private Long id;

    @Column(value = "name")
    protected String name;
}
