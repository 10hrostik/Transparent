package com.api.services.attachments;

import com.api.controllers.dto.attachments.UploadAttachmentDto;
import com.api.controllers.mappers.AttachmentMapper;
import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import com.api.entities.attachments.AttachmentUser;
import com.api.repositories.attachments.AttachmentRepository;
import com.api.repositories.attachments.AttachmentUserRepository;
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
public abstract class LocalAttachmentService implements AttachmentProvider {
    @Value("${attachments.dir}")
    protected String folderPath;

    private final AttachmentMapper attachmentMapper;

    protected final AttachmentRepository attachmentRepository;

    protected final AttachmentUserRepository attachmentUserRepository;

    @Override
    public Flux<Attachment> getUserAttachments(Long userId, AttachmentType attachmentType) {
        return attachmentUserRepository.findAttachmentUserByUserId(userId)
            .flatMap(attachmentUser -> attachmentRepository
                .findAttachmentByIdAndAttachmentType(attachmentUser.getAttachmentId(), attachmentType));
    }

    @Override
    public Flux<Attachment> getUserAttachments(Long userId) {
        return attachmentUserRepository.findAttachmentUserByUserId(userId)
            .flatMap(attachmentUser -> attachmentRepository.findById(attachmentUser.getAttachmentId()));
    }

    @Override
    public Mono<Attachment> getAttachment(Long id) {
        return attachmentRepository.findById(id);
    }

    @Override
    public Mono<Attachment> upload(Mono<FilePart> filePart, Long userId, AttachmentType attachmentType) {
        return filePart.flatMap(file -> this.uploadFile(file, userId, attachmentType));
    }

    @Override
    public Flux<Attachment> getAttachments() {
        return null;
    }

    @Override
    public Flux<Attachment> upload(Flux<FilePart> fileParts, Long userId, AttachmentType attachmentType) {
        return fileParts.flatMap(file -> this.uploadFile(file, userId, attachmentType));
    }

    private Mono<Attachment> saveTransaction(UploadAttachmentDto uploadAttachment) {
        return Objects.equals(AttachmentType.IMAGE, uploadAttachment.getAttachmentType()) ?
            attachmentRepository.hasMainAttachmentUserByUserId(uploadAttachment.getUserId())
                .flatMap(value -> saveTransaction(uploadAttachment, false))
                .switchIfEmpty(saveTransaction(uploadAttachment, true)) :
            saveTransaction(uploadAttachment, false);
    }

    private Mono<Attachment> saveTransaction(UploadAttachmentDto uploadAttachment, boolean isMain) {
        Attachment attachment = attachmentMapper.asAttachment(uploadAttachment.getFileName(), uploadAttachment.getAttachmentType(),
            uploadAttachment.getUrl(), uploadAttachment.getContentType(), isMain, uploadAttachment.getUserId());

        return attachmentRepository.save(attachment).map(this::saveTransaction);
    }

    private Attachment saveTransaction(Attachment attachment) {
        AttachmentUser attachmentUser = attachmentMapper.asAttachmentUser(attachment);
        attachmentUserRepository.save(attachmentUser).delaySubscription(Duration.of(1, ChronoUnit.MILLIS)).subscribe();
        return attachment;
    }

    private Mono<Attachment> uploadFile(FilePart file, Long userId, AttachmentType attachmentType) {
        String filename = file.filename();
        File path = new File(folderPath, filename);
        String contentType = Objects.requireNonNull(file.headers().get(HttpHeaders.CONTENT_TYPE)).getFirst();
        UploadAttachmentDto uploadAttachmentDto = UploadAttachmentDto.builder()
            .fileName(filename)
            .userId(userId)
            .attachmentType(attachmentType)
            .contentType(contentType)
            .url(folderPath + filename)
            .build();

        return file.transferTo(path)
            .then(saveTransaction(uploadAttachmentDto));
    }
}
