package com.fm.smartlearningplatform.common.dto.response;


import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.FileType;
import com.fm.smartlearningplatform.common.enums.StorageProvider;

import java.time.Instant;

public record FileResponse(

        String id,

        String publicId,

        String url,

        String fileName,

        String contentType,

        Long size,

        FileType type,

        StorageProvider provider,

        String hash,

        FileStatus status


) {
}
