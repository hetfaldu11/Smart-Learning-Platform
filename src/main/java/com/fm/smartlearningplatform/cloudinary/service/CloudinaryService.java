package com.fm.smartlearningplatform.cloudinary.service;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.cloudinary.enums.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


public interface CloudinaryService {

    CloudinaryUploadResponse uploadImage(
            MultipartFile file,
            String folder,
            String publicId

    ) throws IOException;

    CloudinaryUploadResponse uploadVideo(
            MultipartFile file,
            String folder,
            String publicId
    ) throws IOException;

    CloudinaryUploadResponse uploadPdf(
            MultipartFile file,
            String folder,
            String publicId
    ) throws IOException;

    void delete(
            String publicId,
            MediaType mediaType

    ) throws IOException;


}