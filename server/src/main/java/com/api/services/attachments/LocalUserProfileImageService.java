package com.api.services.attachments;

import com.api.controllers.dto.attachments.UploadAttachmentDto;
import com.api.controllers.dto.attachments.UserProfileImageDto;
import com.api.controllers.mappers.AttachmentMapper;
import com.api.entities.attachments.AttachmentEntity;
import com.api.entities.attachments.AttachmentType;
import com.api.entities.attachments.UserProfileImage;
import com.api.repositories.attachments.AttachmentRepository;
import com.api.repositories.attachments.AttachmentUserRepository;
import com.api.repositories.attachments.UserProfileImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Service
public class LocalUserProfileImageService extends LocalAttachmentService<UserProfileImage> implements MediaAttachmentProvider<UserProfileImage> {

  @Value("${user.profile.image.dir}")
  private String userProfileImageDir;

  @Autowired
  private UserProfileImageRepository repository;

  public LocalUserProfileImageService(AttachmentUserRepository attachmentUserRepository, AttachmentMapper attachmentMapper) {
    super(attachmentMapper, attachmentUserRepository);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Mono<ResponseEntity<UserProfileImageDto>> uploadUserImage(Mono<FilePart> file, long userId) {
    return upload(file, userId).onErrorReturn(IOException.class, ResponseEntity.internalServerError().build());
  }

  @Override
  public Mono<ResponseEntity<?>> getUserImage(long userId) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Flux<UserProfileImageDto> getUserImages(long userId) {
    return getUserAttachments(userId, AttachmentType.IMAGE)
        .map(userProfileImage -> UserProfileImageDto.builder()
            .id(userProfileImage.getId())
            .filename(userProfileImage.getFilename())
            .attachmentType(userProfileImage.getAttachmentType())
            .contentType(userProfileImage.getContentType())
            .main(userProfileImage.getMain())
            .build());
  }

  @Override
  @Transactional(readOnly = true)
  public Mono<ResponseEntity<byte[]>> getUserMainImage(long userId) {
    return repository.findByMainIsTrueAndCreatedBy(userId).map(this::convertImage);
  }

  @Override
  protected Mono<UserProfileImage> instantiateAttachment(UploadAttachmentDto uploadAttachmentDto) {
    return repository.existsByMainIsTrueAndCreatedBy(uploadAttachmentDto.getUserId()).map(exists -> {
      UserProfileImage userProfileImage = new UserProfileImage(!exists);
      return (UserProfileImage) attachmentMapper.asAttachment(userProfileImage, uploadAttachmentDto);
    });
  }

  @Override
  protected AttachmentRepository<UserProfileImage> getRepository() {
    return repository;
  }

  private Mono<ResponseEntity<UserProfileImageDto>> upload(Mono<FilePart> file, long userId) {
    return upload(file, userId, AttachmentType.IMAGE,  userProfileImageDir + userId + "/")
        .map(userProfileImage -> {
          UserProfileImageDto userProfileImageDto = UserProfileImageDto.builder()
              .id(userProfileImage.getId())
              .contentType(userProfileImage.getContentType())
              .main(userProfileImage.getMain())
              .attachmentType(userProfileImage.getAttachmentType())
              .filename(userProfileImage.getFilename())
              .build();

          return ResponseEntity.ok(userProfileImageDto);
        });
  }

  private ResponseEntity<byte[]> convertImage(AttachmentEntity image) {
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
