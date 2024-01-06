package com.api.entities.rooms;

import lombok.Data;

import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "chats")
@EqualsAndHashCode(callSuper = true)
public class Chat extends Room {
    @Column(value = "user_chat_id")
    private Long userChatId;

    @Column(value = "active")
    private Boolean active;
}
