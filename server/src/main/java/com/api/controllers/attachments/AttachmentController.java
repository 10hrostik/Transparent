package com.api.controllers.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.services.attachments.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/secured/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/photos/user/{id}/all/")
    public Flux<AttachmentDto> getUserPhotos(@PathVariable Long id) {
        return attachmentService.getUserPhotos(id);
    }

    @GetMapping("/photos/user/{id}")
    public Mono<ResponseEntity<?>> getUserPhoto(@PathVariable Long id) {
        return attachmentService.getPhoto(id);
    }
}
