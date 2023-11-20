package com.api.entities.chat;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_chat")
public class UserChat {
    @Id
    private Long id;

    private Long chatId;

    private Long userId;
}
