package com.api.services.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.controllers.dto.attachments.ImageDto;
import com.api.controllers.mappers.AttachmentMapper;
import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import com.api.repositories.attachments.AttachmentRepository;
import com.api.repositories.attachments.AttachmentUserRepository;
import lombok.extern.slf4j.Slf4j;
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
public class LocalImageService extends LocalAttachmentService implements ImageProvider {
  public LocalImageService(AttachmentRepository attachmentRepository, AttachmentUserRepository attachmentUserRepository,
                           AttachmentMapper attachmentMapper) {
    super(attachmentMapper, attachmentRepository, attachmentUserRepository);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Mono<ResponseEntity<ImageDto>> uploadUserImage(Mono<FilePart> image, Long userId) {
    return upload(image, userId, AttachmentType.IMAGE)
        .map(attachment -> {
          ImageDto imageDto = ImageDto.builder()
              .id(attachment.getId())
              .contentType(attachment.getContentType())
              .main(attachment.getMain())
              .attachmentType(attachment.getAttachmentType())
              .fileName(attachment.getFilename())
              .build();

          return ResponseEntity.ok(imageDto);
        })
        .onErrorReturn(IOException.class, ResponseEntity.internalServerError().build());
  }

  @Override
  public Mono<ResponseEntity<?>> getUserImage(Long userId) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Flux<AttachmentDto> getUserImages(Long userId) {
    return getUserAttachments(userId, AttachmentType.IMAGE)
        .map(attachment -> ImageDto.builder()
            .id(attachment.getId())
            .contentType(attachment.getContentType())
            .main(attachment.getMain())
            .build());
  }

  @Override
  public Mono<ResponseEntity<byte[]>> getUserMainImage(Long userId) {
    return getUserAttachments(userId, AttachmentType.IMAGE)
        .filter(Attachment::getMain)
        .map(this::convertImage)
        .last(ResponseEntity.status(HttpStatus.NO_CONTENT)
            .contentType(MediaType.IMAGE_GIF)
            .body(null));
  }

  private ResponseEntity<byte[]> convertImage(Attachment image) {
    try {
      byte[] bytes = Files.readAllBytes(new File(image.getUrl()).toPath());
      if (image.getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.IMAGE_PNG)
            .body(bytes);
      }
      if (image.getContentType().equals("image/jpg") || image.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
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
