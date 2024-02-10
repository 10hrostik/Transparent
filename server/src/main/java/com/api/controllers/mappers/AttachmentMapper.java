package com.api.controllers.mappers;

import com.api.entities.attachments.Attachment;
import com.api.entities.attachments.AttachmentType;
import com.api.entities.attachments.AttachmentUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AttachmentMapper {
   public abstract Attachment asAttachment(String filename, AttachmentType attachmentType, String url, String contentType, boolean main, long userId);

   @Mapping(target = "attachmentId", source = "attachment.id")
   @Mapping(target = "id", ignore = true)
   public abstract AttachmentUser asAttachmentUser(Attachment attachment);
}
