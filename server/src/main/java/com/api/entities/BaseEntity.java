package com.api.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class BaseEntity {
  @Id
  private Long id;
}
