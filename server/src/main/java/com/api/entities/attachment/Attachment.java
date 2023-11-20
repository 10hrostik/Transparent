package com.api.entities.attachment;

import com.api.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@NoArgsConstructor
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

    public Attachment(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }
}


