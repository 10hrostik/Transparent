package com.api.services.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttachmentService {
  Flux<AttachmentDto> getUserPhotos(Long userId);

  Mono<ResponseEntity<?>> getPhoto(Long id);


}
