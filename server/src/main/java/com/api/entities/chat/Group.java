package com.api.entities.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table(name = "groups")
public class Group extends Room {
    private Long userChatId;

    private Boolean active;
}
