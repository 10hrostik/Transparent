package com.api.controllers.Attachments;

import com.api.services.attachments.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/secured/attachment")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/photos/user/{id}")
    public Flux<AttachmentDto> getUserPhotos(@PathVariable Long id) {
        return attachmentService.getUserPhotos(id);
    }
}
