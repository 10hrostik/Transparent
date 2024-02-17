package com.api.services.attachments;

import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttachmentProvider <T extends Attachment> {
  Flux<T> getUserAttachments(Long userId);

  Flux<T> getAttachments();

  Flux<T> getUserAttachments(Long userId, AttachmentType attachmentType);

  Mono<T> getAttachment(Long id);

  Mono<T> upload(Mono<FilePart> file, Long userId, AttachmentType attachmentType, String dir, boolean isMain);

  Flux<T> upload(Flux<FilePart> file, Long userId, AttachmentType attachmentType, String dir, boolean isMain);
}
