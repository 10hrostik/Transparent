package com.api.controllers.dto.users;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InitUserDto {
  @NotNull
  private String credential;

  private String password;
}
