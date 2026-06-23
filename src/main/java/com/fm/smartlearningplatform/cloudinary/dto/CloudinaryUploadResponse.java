package com.fm.smartlearningplatform.cloudinary.dto;

import com.fm.smartlearningplatform.common.enums.FileType;

public record CloudinaryUploadResponse(
        String url,
        String publicId,
        FileType fileType
) {}
