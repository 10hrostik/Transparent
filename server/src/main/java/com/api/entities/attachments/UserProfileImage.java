package com.api.entities.attachments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_profile_images")
public class UserProfileImage extends MediaAttachment {

  @Column(value = "main")
  private Boolean main;

  public UserProfileImage(Boolean main) {
    this.main = main;
  }
}
