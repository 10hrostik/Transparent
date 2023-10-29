package com.api.controllers.Attachments;

import com.api.services.attachments.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/secured/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/photos/user/all/{id}")
    public Flux<AttachmentDto> getUserPhotos(@PathVariable Long id) {
        return attachmentService.getUserPhotos(id);
    }

    @GetMapping("/photos/user/{id}")
    public Mono<ResponseEntity<?>> getUserPhoto(@PathVariable Long id) {
        return attachmentService.getPhoto(id);
    }
}
