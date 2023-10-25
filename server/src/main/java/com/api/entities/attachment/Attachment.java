package com.api.entities.attachment;

import com.api.entities.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table(name = "attachments")
public class Attachment {
    public enum Type {
        IMAGE,
        GIF,
        DOCUMENT
    }

    @Id
    private Long id;

    @Column(value = "filename")
    private String filename;

    @Column(value = "url")
    private String url;

    @Column(value = "content_type")
    private String contentType;

    @Column(value = "attachment_type")
    private Type attachmentType;

    @Transient
    private Set<User> users;

    public Attachment() {}

    public Attachment(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Type getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(Type attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
