package com.api.entities.rooms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "channels")
@EqualsAndHashCode(callSuper = true)
public class Channel extends Room {
  @Column(value = "user_chat_id")
  private Long userChannelId;

  @Column(value = "active")
  private Boolean active;
}
