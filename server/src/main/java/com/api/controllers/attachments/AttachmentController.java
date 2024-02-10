package com.api.controllers.attachments;

import com.api.controllers.dto.attachments.AttachmentDto;
import com.api.controllers.dto.attachments.ImageDto;
import com.api.services.attachments.LocalImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/secured/attachments")
public class AttachmentController {
    private final LocalImageService imageService;

    @GetMapping("/photos/user/{id}/all")
    public Flux<AttachmentDto> getUserPhotos(@PathVariable Long id) {
        return imageService.getUserImages(id);
    }

    @GetMapping("/photos/user/{id}")
    public Mono<ResponseEntity<byte[]>> getUserPhoto(@PathVariable Long id) {
        return imageService.getUserMainImage(id);
    }

    @PostMapping(value = "/photos/user/{id}")
    public Mono<ResponseEntity<ImageDto>> uploadUserImage(@RequestPart("image") Mono<FilePart> image, @PathVariable long id) {
        try {
            return imageService.uploadUserImage(image, id);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
