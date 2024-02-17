package com.api.repositories.attachments;

import com.api.entities.attachments.Document;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends AttachmentRepository<Document> {
}
