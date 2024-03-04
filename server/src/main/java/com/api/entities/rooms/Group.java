package com.api.entities.rooms;

import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "groups")
@EqualsAndHashCode(callSuper = true)
public class Group extends Room {
  @Column(value = "user_group_id")
  private Long userGroupId;

  @Column(value = "active")
  private Boolean active;
}
