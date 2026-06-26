package com.fm.smartlearningplatform.cloudinary.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.cloudinary.enums.MediaType;
import com.fm.smartlearningplatform.common.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl
        implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public CloudinaryUploadResponse uploadImage(
            MultipartFile file, String folder, String publicId) throws IOException
    {

        Map<String, Object> result = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap("resource_type", "image",
                                "folder", folder,
                                "public_id",publicId,
                                "overwrite", true)
        );
        return buildResponse(result);
    }
    @Override
    public CloudinaryUploadResponse uploadVideo(MultipartFile file, String folder,String publicId)
            throws IOException {

        Map<String, Object> result = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap("resource_type", "video", "folder", folder,
                        "public_id", publicId,
                        "overwrite", true)
                );

        return buildResponse(result);
    }

    @Override
    public CloudinaryUploadResponse uploadPdf(
            MultipartFile file, String folder,String publicId) throws IOException
    {

        Map<String, Object> result =
                cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap("resource_type", "raw", "folder", folder,
                                    "public_id", publicId,
                                    "overwrite", true)
                );

        return buildResponse(result);
    }

    @Override
    public void delete(String publicId, MediaType mediaType) throws IOException {

        if (publicId == null || publicId.isBlank()) {
            return;
        }

        String resourceType =
                switch (mediaType) {

                    case IMAGE -> "image";

                    case VIDEO -> "video";

                    case PDF -> "raw";
                };

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap("resource_type", resourceType)
        );
    }

    private FileType mapFileType(String resourceType) {

        return switch (resourceType.toLowerCase()) {
            case "image" -> FileType.IMAGE;
            case "video" -> FileType.VIDEO;
            case "raw" -> FileType.PDF;
            default -> throw new IllegalArgumentException(
                    "Unsupported resource type: " + resourceType
            );
        };
    }
    private CloudinaryUploadResponse buildResponse(Map<String, Object> result) {

        String resourceType = (String) result.get("resource_type");
        Integer durationSeconds = null;

        if ("video".equals(resourceType)) {

            Object duration = result.get("duration");

            if (duration instanceof Number number) {
                durationSeconds = number.intValue();
            }
        }

        return new CloudinaryUploadResponse(
                (String) result.get("secure_url"),
                (String) result.get("public_id"),
                mapFileType(resourceType),
                durationSeconds
        );
    }
}
