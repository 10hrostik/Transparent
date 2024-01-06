package com.api.services.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentUser;
import com.api.repositories.attachments.AttachmentRepository;
import com.api.repositories.attachments.AttachmentUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    @Value("${attachments.dir}")
    private String folderPath;

    private final AttachmentRepository attachmentRepository;

    private final AttachmentUserRepository attachmentUserRepository;

    public Flux<AttachmentDto> getUserPhotos(Long userId) {
        Flux<AttachmentUser> attachmentUserFlux = attachmentUserRepository.findAttachmentUserByUserId(userId);
        return attachmentUserFlux
                .flatMap(attachmentUser -> attachmentRepository.findById(attachmentUser.getAttachmentId()))
                .filter(attachment -> attachment.getAttachmentType().equals(Attachment.Type.IMAGE))
                .map(attachment -> new AttachmentDto(attachment.getId(), attachment.getUrl(), attachment.getContentType())
            );
    }

    public Mono<ResponseEntity<?>> getPhoto(Long id) {
        return attachmentRepository.findById(id)
                .publishOn(Schedulers.boundedElastic())
                .map(photo -> {
                    try {
                        byte[] bytes = Files.readAllBytes(new File(folderPath + photo.getUrl()).toPath());
                        if (photo.getContentType().equals("image/png")) {
                            return ResponseEntity.status(HttpStatus.OK)
                                    .contentType(MediaType.IMAGE_PNG)
                                    .body(bytes);
                        }
                        if (photo.getContentType().equals("image/jpg") || photo.getContentType().equals("image/jpeg")) {
                            return ResponseEntity.status(HttpStatus.OK)
                                    .contentType(MediaType.IMAGE_JPEG)
                                    .body(bytes);
                        }
                        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.IMAGE_GIF)
                                .body(bytes);
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.OK)
                                .contentType(MediaType.IMAGE_GIF)
                                .body(null);
                    }
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.IMAGE_GIF)
                        .body(null));
    }
}
