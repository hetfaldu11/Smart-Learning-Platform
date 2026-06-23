package com.fm.smartlearningplatform.common.mapper;

import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import com.fm.smartlearningplatform.common.model.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
    public interface FileMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "hash", ignore = true)
        @Mapping(target = "deletedAt", ignore = true)

        @Mapping(target = "publicId", source = "response.publicId")
        @Mapping(target = "url", source = "response.url")
        @Mapping(target = "type", source = "response.fileType")

        @Mapping(target = "fileName", source = "file.originalFilename")
        @Mapping(target = "contentType", source = "file.contentType")
        @Mapping(target = "size", source = "file.size")

        @Mapping(target = "provider", source = "provider")
        @Mapping(target = "status", source = "status")
        File toEntity(
                MultipartFile file,
                CloudinaryUploadResponse response,
                StorageProvider provider,
                FileStatus status
        );
    }// areee mota hosiyar bhai tu full tamara je

