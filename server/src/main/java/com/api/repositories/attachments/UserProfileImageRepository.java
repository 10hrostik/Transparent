package com.api.repositories.attachments;

import com.api.entities.attachments.UserProfileImage;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserProfileImageRepository extends AttachmentRepository<UserProfileImage> {
  Mono<Boolean> existsByMainIsTrueAndCreatedBy(Long userId);

  Mono<UserProfileImage> findByMainIsTrueAndCreatedBy(Long userId);

}
