package com.fm.smartlearningplatform.cloudinary.validation;

import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class FileValidation {

    private static final Set<String> ALLOWED_IMAGE_TYPES= Set.of(
            "image/jpeg",

            "image/png",

            "image/webp");

    private static final Set<String> ALLOWED_VIDEO_TYPES = Set.of(

            "video/mp4",

            "video/quicktime",

            "video/x-matroska"
    );

    private static final Set<String>
            ALLOWED_PDF_TYPES = Set.of(

            "application/pdf"
    );

    @Value("${media.image.max-size}")
    private DataSize MAX_IMAGE_SIZE;

    @Value("${media.video.max-size}")
    private DataSize MAX_VIDEO_SIZE;

    @Value("${media.pdf.max-size}")
    private DataSize MAX_PDF_SIZE;

    public void  validateImage(MultipartFile file) {
        String contentType= file.getContentType();

        if(contentType==null || !ALLOWED_IMAGE_TYPES.contains(contentType))
        {
            throw new IllegalArgumentException("Invalid image formate");
        }
        validateFileSize(
                file,
                MAX_IMAGE_SIZE,
                "Image"
        );
    }

    public  void  validateVideo(MultipartFile file)
    {
        String contentType= file.getContentType();

        if(contentType==null || !ALLOWED_VIDEO_TYPES.contains(contentType))
        {
            throw  new InvalidFileException("Invalid video formate");
        }

        validateFileSize(
                file,
                MAX_VIDEO_SIZE,
                "Video"
        );
    }

    public void validatePdf(MultipartFile file)
    {
        String contentType= file.getContentType();
        if(contentType ==null || !ALLOWED_PDF_TYPES.contains(contentType))
        {
            throw new InvalidFileException("Invalid pdf formate");
        }

        validateFileSize(
                file,
                MAX_PDF_SIZE,
                "PDF"
        );

    }

    // ─── Helper ───────────────────────────────────────────────

    private void validateFileSize(
            MultipartFile file,
            DataSize maxSize,
            String fileType
    ) {

        if (file.getSize() > maxSize.toBytes()) {

            throw new InvalidFileException(
                    fileType + " size exceeds limit."
            );
        }
    }

}
