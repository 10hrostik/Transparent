package com.api.entities.attachment;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "attachment_user")
public class AttachmentUser {
    @Id
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "attachment_id")
    private Long attachmentId;
}
