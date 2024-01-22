package com.api.services.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.entities.attachments.AttachmentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttachmentService {
  Flux<AttachmentDto> getUserPhotos(Long userId);

  Mono<ResponseEntity<?>> getPhoto(Long id);

  void upload(MultipartFile file, Long userId, AttachmentType attachmentType);
}
