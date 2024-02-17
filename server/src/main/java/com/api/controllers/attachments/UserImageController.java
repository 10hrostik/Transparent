package com.api.controllers.attachments;

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

    private final MediaAttachmentProvider<UserProfileImage> userProfileImageService;

    @GetMapping("/all")
    public Flux<UserProfileImageDto> getUserImages(@PathVariable Long id) {
        return userProfileImageService.getUserImages(id);
    }

    @GetMapping("/{imageId}")
    public Mono<ResponseEntity<byte[]>> getUserImage(@PathVariable(name = "id") Long userId, @PathVariable Long imageId) {
        return userProfileImageService.getImage(imageId, userId);
    }

    @GetMapping("/main")
    public Mono<ResponseEntity<byte[]>> getUserMainImage(@PathVariable Long id) {
        return userProfileImageService.getUserMainImage(id);
    }

    @PostMapping
    public Mono<ResponseEntity<byte[]>> uploadUserImage(@RequestPart("image") Mono<FilePart> image, @PathVariable long id) {
        try {
            return userProfileImageService.uploadUserImage(image, id);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
