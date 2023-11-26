package com.api.entities.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "channels")
public class Channel extends Room {
    private Long userChatId;

    private Boolean active;
}
