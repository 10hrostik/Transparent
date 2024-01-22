package com.api.controllers.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.services.attachments.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/secured/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @GetMapping("/photos/user/{id}/all")
    public Flux<AttachmentDto> getUserPhotos(@PathVariable Long id) {
        return attachmentService.getUserPhotos(id);
    }

    @GetMapping("/photos/user/{id}")
    public Mono<ResponseEntity<?>> getUserPhoto(@PathVariable Long id) {
        return attachmentService.getPhoto(id);
    }

    @PostMapping(value = "/photos/user/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<?>> upload(ServerHttpRequest serverWebExchange) {
        try {
            return Mono.just(ResponseEntity.ok().build());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
