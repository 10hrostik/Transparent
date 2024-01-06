package com.api.controllers.dto.attachments;

import lombok.Data;

@Data
public class AttachmentDto {
    private long id;

    private String url;

    private String imageType;

    public AttachmentDto(long id, String url, String imageType) {
        this.id = id;
        this.url = url;
        this.imageType = imageType;
    }
}
