package com.api.repositories.attachments;

import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;

@NoRepositoryBean
public interface AttachmentRepository<T extends Attachment> extends R2dbcRepository<T, Long> {
  Flux<T> findAttachmentByIdAndAttachmentType(long id, AttachmentType attachmentType);

}
