package com.api.controllers.Attachments;

public class AttachmentDto {
    private byte[] file;

    private String imageType;

    public AttachmentDto(byte[] file, String imageType) {
        this.file = file;
        this.imageType = imageType;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
