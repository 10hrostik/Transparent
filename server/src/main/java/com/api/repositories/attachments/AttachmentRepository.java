package com.api.repositories.attachments;

import com.api.entities.attachments.AttachmentEntity;
import com.api.entities.attachments.AttachmentType;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface AttachmentRepository<T extends AttachmentEntity> extends R2dbcRepository<T, Long> {
  Flux<T> findAttachmentByIdAndAttachmentType(long id, AttachmentType attachmentType);

  Flux<T> findAllByCreatedBy(Long userId);

  Mono<T> findByIdAndCreatedBy(Long id, Long createdBy);

}
