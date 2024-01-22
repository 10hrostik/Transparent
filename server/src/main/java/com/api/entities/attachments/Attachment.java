package com.api.entities.attachments;

import com.api.entities.BaseEntity;
import com.api.entities.users.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "attachments")
@EqualsAndHashCode(callSuper = true)
public class Attachment extends BaseEntity {

    @Column(value = "filename")
    private String filename;

    @Column(value = "url")
    private String url;

    @Column(value = "content_type")
    private String contentType;

    @Column(value = "attachment_type")
    private AttachmentType attachmentType;

    @Column(value = "main")
    private Boolean main = false;

    @Transient
    private Set<User> users;

}


