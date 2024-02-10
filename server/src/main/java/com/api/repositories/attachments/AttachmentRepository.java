package com.api.repositories.attachments;

import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AttachmentRepository extends R2dbcRepository<Attachment, Long> {
  Flux<Attachment> findAttachmentByIdAndAttachmentType(long id, AttachmentType attachmentType);

  @Query("SELECT COUNT(attachment_type) FROM attachments" +
      " JOIN attachments_users ON attachments_users.user_id = :userId" +
      " WHERE attachment_type = 'IMAGE' AND attachments.main = true" +
      " GROUP BY user_id" +
      " HAVING COUNT(attachment_type) > 0")
  Mono<Integer> hasMainAttachmentUserByUserId(@Param("userId") Long userId);
}
