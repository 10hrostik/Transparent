package com.api.controllers.users;

import com.api.controllers.dto.attachments.UserProfileImageDto;
import com.api.entities.attachments.UserProfileImage;
import com.api.services.attachments.MediaAttachmentProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/user/{id}/image")
public class UserImageController {

    private final MediaAttachmentProvider<UserProfileImage> imageService;

    @GetMapping("/all")
    public Flux<UserProfileImageDto> getUserImages(@PathVariable Long id) {
        return imageService.getUserImages(id);
    }

    @GetMapping
    public Mono<ResponseEntity<byte[]>> getUserImage(@PathVariable Long id) {
        return imageService.getUserMainImage(id);
    }

    @PostMapping
    public Mono<ResponseEntity<UserProfileImageDto>> uploadUserImage(@RequestPart("image") Mono<FilePart> image, @PathVariable long id) {
        try {
            return imageService.uploadUserImage(image, id);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
