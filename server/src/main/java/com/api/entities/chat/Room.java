package com.api.entities.chat;

import com.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Room extends BaseEntity {
    @Column(value = "name")
    protected String name;

    @Column(value = "created_on")
    protected LocalDate createdOn;

    @Column(value = "created_by")
    protected Long createdBy;
}
