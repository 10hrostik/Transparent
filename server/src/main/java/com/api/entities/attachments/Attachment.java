package com.api.entities.attachments;

import com.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Attachment extends BaseEntity {

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
    private Long userId;

}


