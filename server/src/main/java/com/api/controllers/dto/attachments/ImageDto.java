package com.api.controllers.dto.attachments;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageDto extends AttachmentDto {

  private boolean main;

}
