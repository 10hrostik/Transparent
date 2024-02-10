package com.api.controllers.dto.attachments;

import com.api.entities.attachments.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {
    private long id;

    private String fileName;

    private AttachmentType attachmentType;

    private String contentType;
}
