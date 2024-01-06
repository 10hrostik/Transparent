package com.api.entities.chat;

import com.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "user_chat")
@EqualsAndHashCode(callSuper = true)
public class UserChat extends BaseEntity {
    @Column(value = "chat_id")
    private Long chatId;

    @Column(value = "user_id")
    private Long userId;
}
