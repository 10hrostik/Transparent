package com.api.entities.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "group_users")
@EqualsAndHashCode(callSuper = true)
public class UserGroup extends Room {
  @Column(value = "group_id")
  private Long groupId;

  @Column(value = "user_id")
  private Long userId;
}
