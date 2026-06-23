package com.fm.smartlearningplatform.common.service;

import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import com.fm.smartlearningplatform.common.mapper.FileMapper;
import com.fm.smartlearningplatform.common.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class FileService {

    private final FileMapper fileMapper;

    public File createFile(
            MultipartFile file,
            CloudinaryUploadResponse response,
            StorageProvider storageProvider,
            FileStatus fileStatus
    ) {

        return File.builder()
                .publicId(response.publicId())
                .url(response.url())
                .fileName(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .type(response.fileType())
                .provider(storageProvider)
                .status(fileStatus)
                .build();
    }

    public void softDelete(File file) {
        if (file == null) {
            return;
        }
        file.setDeletedAt(LocalDateTime.now());
        file.setStatus(FileStatus.DELETED);
    }
}
