package com.api.controllers.dto.attachments;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UploadAttachmentDto extends AttachmentDto {
  private String url;

  private long userId;
}
