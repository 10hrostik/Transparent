package com.api.entities.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "channels_users")
@EqualsAndHashCode(callSuper = true)
public class UserChannel extends Room {
  @Column(value = "channel_id")
  private Long channelId;

  @Column(value = "user_id")
  private Long userId;
}
