package com.api.entities.chat;

import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "groups")
@EqualsAndHashCode(callSuper = true)

public class Group extends Room {
    private Long userChatId;

    private Boolean active;
}
