package com.api.services.attachments;

import com.api.controllers.Attachments.AttachmentDto;
import com.api.entities.attachment.Attachment;
import com.api.entities.attachment.AttachmentUser;
import com.api.repositories.AttachmentRepository;
import com.api.repositories.AttachmentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentUserRepository attachmentUserRepository;

    @Value("${attachments.dir}")
    private String folderPath;

    public Flux<AttachmentDto> getUserPhotos(Long userId) {
        Flux<AttachmentUser> attachmentUserFlux = attachmentUserRepository.findAttachmentUserByUserId(userId);
        return attachmentUserFlux
                .flatMap(attachmentUser -> attachmentRepository.findById(attachmentUser.getAttachmentId()))
                .filter(attachment -> attachment.getAttachmentType().equals(Attachment.Type.IMAGE))
                .map(attachment -> {
                    try {
                        return new AttachmentDto(Files
                                .readAllBytes(new File(folderPath + attachment.getUrl()).toPath()), attachment.getContentType());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            );
    }
}
