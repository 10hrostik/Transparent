package com.api.controllers.mappers;

import com.api.controllers.dto.attachments.UploadAttachmentDto;
import com.api.entities.attachments.AttachmentEntity;
import com.api.entities.attachments.AttachmentUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public abstract class AttachmentMapper {
  @Mapping(target = "createdBy", source = "uploadAttachmentDto.userId")
  @Mapping(target = "createdOn", expression = "java(LocalDate.now())")
  @Mapping(target = "id", ignore = true)
  public abstract AttachmentEntity asAttachment(@MappingTarget AttachmentEntity attachment, UploadAttachmentDto uploadAttachmentDto);

  @Mapping(target = "attachmentId", source = "attachment.id")
  @Mapping(target = "id", ignore = true)
  public abstract AttachmentUser asAttachmentUser(AttachmentEntity attachment);
}
