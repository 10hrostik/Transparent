package com.api.repositories.attachments;

import com.api.entities.attachments.Attachment;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends R2dbcRepository<Attachment, Long> {
}
