package com.api.repositories.attachments;

import com.api.entities.attachments.AttachmentUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttachmentUserRepository extends R2dbcRepository<AttachmentUser, Long> {
  Flux<AttachmentUser> findAttachmentUserByUserId(Long userId);
}
