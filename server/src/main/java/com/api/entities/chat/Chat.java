package com.api.entities.chat;

import lombok.Data;

import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "chats")
@EqualsAndHashCode(callSuper = true)

public class Chat extends Room {
    private Long userChatId;

    private Boolean active;
}
