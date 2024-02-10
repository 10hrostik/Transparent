package com.api.services.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.controllers.dto.attachments.ImageDto;
import jakarta.servlet.http.Part;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImageProvider extends AttachmentProvider {
  Mono<ResponseEntity<ImageDto>> uploadUserImage(Mono<FilePart> image, Long userId);

  Mono<ResponseEntity<?>> getUserImage(Long userId);

  Flux<AttachmentDto> getUserImages(Long userId);

  Mono<ResponseEntity<byte[]>> getUserMainImage(Long userId);
}
