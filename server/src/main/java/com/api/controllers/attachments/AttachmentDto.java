package com.api.controllers.attachments;

public class AttachmentDto {
    private long id;

    private String url;

    private String imageType;

    public AttachmentDto(long id, String url, String imageType) {
        this.id = id;
        this.url = url;
        this.imageType = imageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
