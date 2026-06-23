package com.fm.smartlearningplatform.common.dto.request;


import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.FileType;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateFileRequest(

        @NotBlank(message = "Public id is required")
        @Size(max = 255, message = "Public id cannot exceed 255 characters")
        String publicId,

        @NotBlank(message = "File URL is required")
        @Size(max = 1000, message = "File URL cannot exceed 1000 characters")
        String url,

        @NotBlank(message = "File name is required")
        @Size(max = 255, message = "File name cannot exceed 255 characters")
        String fileName,

        @NotBlank(message = "Content type is required")
        @Size(max = 100, message = "Content type cannot exceed 100 characters")
        String contentType,

        @NotNull(message = "File size is required")
        @Positive(message = "File size must be greater than 0")
        Long size,

        @NotNull(message = "File type is required")
        FileType type,

        @NotNull(message = "Storage provider is required")
        StorageProvider provider,

        @Size(max = 64, message = "Hash cannot exceed 64 characters")
        String hash,

        @NotNull(message = "File status is required")
        FileStatus status
) {

    public CreateFileRequest {

        if (publicId != null) {
            publicId = publicId.trim();
        }

        if (url != null) {
            url = url.trim();
        }

        if (fileName != null) {
            fileName = fileName.trim()
                    .replaceAll("\\s+", " ");
        }

        if (contentType != null) {
            contentType = contentType.trim();
        }

        if (hash != null) {
            hash = hash.trim();
        }
    }
}