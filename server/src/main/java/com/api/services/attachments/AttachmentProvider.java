package com.api.services.attachments;

import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttachmentProvider {
  Flux<Attachment> getUserAttachments(Long userId);

  Flux<Attachment> getAttachments();

  Flux<Attachment> getUserAttachments(Long userId, AttachmentType attachmentType);

  Mono<Attachment> getAttachment(Long id);

  Mono<Attachment> upload(Mono<FilePart> file, Long userId, AttachmentType attachmentType);

  Flux<Attachment> upload(Flux<FilePart> file, Long userId, AttachmentType attachmentType);
}
