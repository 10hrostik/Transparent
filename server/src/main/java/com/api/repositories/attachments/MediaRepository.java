package com.api.repositories.attachments;

import com.api.entities.attachments.Media;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MediaRepository extends AttachmentRepository<Media> {
  @Query("SELECT COUNT(attachment_type) FROM attachments" +
      " JOIN attachments_users ON attachments_users.user_id = :userId" +
      " WHERE attachment_type = 'IMAGE' AND attachments.main = true" +
      " GROUP BY user_id" +
      " HAVING COUNT(attachment_type) > 0")
  Mono<Integer> hasMainByUserId(@Param("userId") Long userId);

  @Query("SELECT * FROM attachments " +
      " JOIN attachments_users ON attachments_users.user_id = :userId AND attachments_users.attachment_id = attachments.id" +
      " WHERE attachment_type = 'IMAGE' AND attachments.main = true")
  Mono<Media> findMediaByMainIsTrueAndUserId(Long userId);
}
