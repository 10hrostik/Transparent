package com.api.services.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.controllers.dto.attachments.ImageDto;
import com.api.entities.attachments.Media;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MediaProvider extends AttachmentProvider<Media> {
  Mono<ResponseEntity<ImageDto>> uploadUserImage(Mono<FilePart> file, long userId);

  Mono<ResponseEntity<?>> getUserImage(long userId);

  Flux<ImageDto> getUserImages(long userId);

  Mono<ResponseEntity<byte[]>> getUserMainImage(long userId);
}
