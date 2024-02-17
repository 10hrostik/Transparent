package com.api.services.attachments;

import com.api.controllers.dto.attachments.ImageDto;
import com.api.controllers.mappers.AttachmentMapper;
import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import com.api.entities.attachments.Media;
import com.api.repositories.attachments.AttachmentRepository;
import com.api.repositories.attachments.AttachmentUserRepository;
import com.api.repositories.attachments.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Primary
@Service
public class LocalMediaService extends LocalAttachmentService<Media> implements MediaProvider {

  @Value("${media.dir}")
  private String mediaDir;

  @Autowired
  private MediaRepository mediaRepository;

  public LocalMediaService(AttachmentUserRepository attachmentUserRepository, AttachmentMapper attachmentMapper) {
    super(attachmentMapper, attachmentUserRepository);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Mono<ResponseEntity<ImageDto>> uploadUserImage(Mono<FilePart> file, long userId) {
    return mediaRepository.hasMainByUserId(userId)
        .flatMap(isMain -> upload(file, userId, false))
        .switchIfEmpty(upload(file, userId, true))
        .onErrorReturn(IOException.class, ResponseEntity.internalServerError().build());
  }

  @Override
  public Mono<ResponseEntity<?>> getUserImage(long userId) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Flux<ImageDto> getUserImages(long userId) {
    return getUserAttachments(userId, AttachmentType.IMAGE)
        .map(media -> ImageDto.builder()
            .id(media.getId())
            .fileName(media.getFilename())
            .attachmentType(media.getAttachmentType())
            .contentType(media.getContentType())
            .main(media.getMain())
            .build());
  }

  @Override
  @Transactional(readOnly = true)
  public Mono<ResponseEntity<byte[]>> getUserMainImage(long userId) {
    return mediaRepository.findMediaByMainIsTrueAndUserId(userId).map(this::convertImage);
  }

  @Override
  protected Attachment instantiateAttachment() {
    return new Media();
  }

  @Override
  protected AttachmentRepository<Media> getRepository() {
    return mediaRepository;
  }

  private Mono<ResponseEntity<ImageDto>> upload(Mono<FilePart> file, long userId, boolean hasMain) {
    return upload(file, userId, AttachmentType.IMAGE, mediaDir, hasMain)
        .map(media -> {
          ImageDto imageDto = ImageDto.builder()
              .id(media.getId())
              .contentType(media.getContentType())
              .main(media.getMain())
              .attachmentType(media.getAttachmentType())
              .fileName(media.getFilename())
              .build();

          return ResponseEntity.ok(imageDto);
        });
  }

  private ResponseEntity<byte[]> convertImage(Attachment image) {
    try {
      byte[] bytes = Files.readAllBytes(new File(image.getUrl()).toPath());
      String contentType = image.getContentType();
      if (contentType.equals(MediaType.IMAGE_PNG_VALUE)) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.IMAGE_PNG)
            .body(bytes);
      }
      if (contentType.equals("image/jpg") || contentType.equals(MediaType.IMAGE_JPEG_VALUE)) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes);
      }
      return ResponseEntity.status(HttpStatus.OK)
          .contentType(MediaType.IMAGE_GIF)
          .body(bytes);
    } catch (IOException exception) {
      log.error(exception.getMessage());
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
