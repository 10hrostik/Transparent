package com.api.utils;

import com.api.entities.attachments.AttachmentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class FileUtils {
  public static boolean createFolderIfNotExists(String uri) {
    File file = new File(uri);
    if (!file.exists()) {
      return file.mkdirs();
    }
    return true;
  }

  public static ResponseEntity<byte[]> convertImage(AttachmentEntity image) {
    try {
      byte[] bytes = Files.readAllBytes(new File(image.getUrl()).toPath());
      String contentType = image.getContentType();
      if (contentType.equals(MediaType.IMAGE_PNG_VALUE)) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.IMAGE_PNG)
            .body(bytes);
      }
      if (contentType.equals("image/jpg") || contentType.equals(MediaType.IMAGE_JPEG_VALUE)) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes);
      }
      return ResponseEntity.status(HttpStatus.OK)
          .contentType(MediaType.IMAGE_GIF)
          .body(bytes);
    } catch (IOException exception) {
      log.error(exception.getMessage());
    }

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
