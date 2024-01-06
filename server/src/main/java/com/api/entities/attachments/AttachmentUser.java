package com.api.entities.attachments;

import com.api.entities.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "attachments_users")
@EqualsAndHashCode(callSuper = true)
public class AttachmentUser extends BaseEntity {
    @Column(value = "user_id")
    private Long userId;

    @Column(value = "attachment_id")
    private Long attachmentId;
}
