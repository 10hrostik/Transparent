package com.api.entities.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table(name = "chats")
public class Chat extends Room {
    private Long userChatId;

    private Boolean active;
}
