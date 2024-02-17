package com.api.repositories.attachments;

import com.api.entities.attachments.DocumentAttachment;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentAttachmentRepository extends AttachmentRepository<DocumentAttachment> {
}
