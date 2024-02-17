package com.api.services.attachments;

import com.api.controllers.dto.attachments.UserProfileImageDto;
import com.api.entities.attachments.MediaAttachment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MediaAttachmentProvider<T extends MediaAttachment> extends AttachmentProvider<T> {
  Mono<ResponseEntity<byte[]>> uploadUserImage(Mono<FilePart> file, long userId);

  Mono<ResponseEntity<byte[]>> getImage(long imageId, long userId);

  Flux<UserProfileImageDto> getUserImages(long userId);

  Mono<ResponseEntity<byte[]>> getUserMainImage(long userId);
}
