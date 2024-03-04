package com.api.services.attachments;

import com.api.controllers.dto.attachments.UploadAttachmentDto;
import com.api.controllers.mappers.AttachmentMapper;
import com.api.entities.attachments.AttachmentEntity;
import com.api.entities.attachments.AttachmentType;
import com.api.entities.attachments.AttachmentUser;
import com.api.entities.attachments.UserProfileImage;
import com.api.repositories.attachments.AttachmentRepository;
import com.api.repositories.attachments.AttachmentUserRepository;
import com.api.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Primary
@RequiredArgsConstructor
@Service
public abstract class LocalAttachmentService<T extends AttachmentEntity> implements AttachmentProvider<T> {

  protected final AttachmentMapper attachmentMapper;

  private final AttachmentUserRepository attachmentUserRepository;

  @Value("${attachments.dir}")
  private String attachemntfolderPath;

  @Override
  public Flux<T> getUserAttachments(Long userId, AttachmentType attachmentType) {
    return attachmentUserRepository.findAttachmentUserByUserId(userId).flatMap(attachmentUser -> getRepository()
      .findAttachmentByIdAndAttachmentType(attachmentUser.getAttachmentId(), attachmentType));
  }

  @Override
  public Flux<T> getUserAttachments(Long userId) {
    return attachmentUserRepository.findAttachmentUserByUserId(userId)
     .flatMap(attachmentUser -> getRepository().findById(attachmentUser.getAttachmentId()));
  }

  @Override
  public Mono<T> getAttachment(Long id) {
    return getRepository().findById(id);
  }

  @Override
  public Mono<T> upload(Mono<FilePart> filePart, Long userId, AttachmentType attachmentType, String dir) {
    return filePart.flatMap(file -> this.uploadFile(file, userId, attachmentType, dir));
  }

  @Override
  public Flux<T> getAttachments() {
    return null;
  }

  @Override
  public Flux<T> upload(Flux<FilePart> fileParts, Long userId, AttachmentType attachmentType, String dir) {
    return fileParts.flatMap(file -> this.uploadFile(file, userId, attachmentType, dir));
  }

  protected abstract Mono<T> instantiateAttachment(UploadAttachmentDto uploadAttachmentDto);

  protected abstract AttachmentRepository<T> getRepository();

  private Mono<T> uploadFile(FilePart file, Long userId, AttachmentType attachmentType, String dir) {
    String filename = file.filename();
    String uri = attachemntfolderPath.concat(dir);
    String contentType = Objects.requireNonNull(file.headers().get(HttpHeaders.CONTENT_TYPE)).getFirst();
    FileUtils.createFolderIfNotExists(uri);
    File path = new File(uri, filename);
    UploadAttachmentDto uploadAttachmentDto = UploadAttachmentDto.builder()
     .filename(filename)
     .userId(userId)
     .attachmentType(attachmentType)
     .contentType(contentType)
     .url(uri + filename)
     .build();
    return file.transferTo(path).then(saveTransaction(uploadAttachmentDto));
  }

  private Mono<T> saveTransaction(UploadAttachmentDto uploadAttachment) {
    return instantiateAttachment(uploadAttachment).flatMap(this::saveAttachment);
  }

  private Mono<T> saveAttachment(T attachment) {
    return getRepository().save(attachment).map(savedAttachment -> savedAttachment instanceof UserProfileImage ? savedAttachment : saveAttachmentUser(savedAttachment));
  }

  private T saveAttachmentUser(T attachment) {
    AttachmentUser attachmentUser = attachmentMapper.asAttachmentUser(attachment);
    attachmentUserRepository.save(attachmentUser).delaySubscription(Duration.of(1, ChronoUnit.MILLIS)).subscribe();
    return attachment;
  }
}
