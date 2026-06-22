package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.cloudinary.constant.CloudinaryFolders;
import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.cloudinary.enums.MediaType;
import com.fm.smartlearningplatform.cloudinary.service.CloudinaryService;
import com.fm.smartlearningplatform.cloudinary.validation.FileValidation;
import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.FileType;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.course.dto.courseMedia.response.CourseMediaResponse;
import com.fm.smartlearningplatform.course.mapper.CourseMediaMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CourseMedia;
import com.fm.smartlearningplatform.course.repository.CourseMediaRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseMediaService {

    private final CourseMediaRepository courseMediaRepository;

    private final CourseRepository courseRepository;

    private final CourseMediaMapper courseMediaMapper;

    private final CloudinaryService cloudinaryService;

    private final FileValidation fileValidation;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseMediaResponse uploadThumbnail(Long courseId, MultipartFile file) throws IOException {
        String folder = getCourseFolder(courseId);

        String publicId = "thumbnail";
        fileValidation.validateImage(file);

        CourseMedia courseMedia = getOrCreateCourseMedia(courseId);
        CloudinaryUploadResponse response = cloudinaryService.uploadImage(file, folder, publicId);

        File thumbnail = File.builder()
                .publicId(response.publicId())
                .url(response.url())
                .fileName(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .type(FileType.IMAGE)
                .provider(StorageProvider.CLOUDINARY)
                .status(FileStatus.UPLOADING)
                .build();
        courseMedia.setThumbnail(thumbnail);
        return courseMediaMapper.toResponse(courseMediaRepository.save(courseMedia));
    }

    @Transactional
    public CourseMediaResponse uploadPromotionalLesson(Long courseId, MultipartFile file) throws IOException {
        String folder = getCourseFolder(courseId);
        String publicId = "promo-video";
        fileValidation.validateVideo(file);

        CourseMedia courseMedia = getOrCreateCourseMedia(courseId);

        CloudinaryUploadResponse response = cloudinaryService.uploadVideo(file, folder, publicId);

        File promotionalLesson = File.builder()
                .publicId(response.publicId())
                .url(response.url())
                .fileName(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .type(FileType.VIDEO)
                .provider(StorageProvider.CLOUDINARY)
                .status(FileStatus.UPLOADING)
                .build();
        courseMedia.setThumbnail(promotionalLesson);
        return courseMediaMapper.toResponse(courseMediaRepository.save(courseMedia));
    }

    @Transactional
    public CourseMediaResponse uploadCertificateTemplate(Long courseId, MultipartFile file) throws IOException {
        String folder = getCourseFolder(courseId);
        String publicId = "certificate-template";
        fileValidation.validatePdf(file);

        CourseMedia courseMedia = getOrCreateCourseMedia(courseId);
        CloudinaryUploadResponse response = cloudinaryService.uploadPdf(file, folder, publicId);

        File certificateTemplate = File.builder()
                .publicId(response.publicId())
                .url(response.url())
                .fileName(file.getName())
                .contentType(file.getContentType())
                .size(file.getSize())
                .type(FileType.IMAGE)
                .provider(StorageProvider.CLOUDINARY)
                .status(FileStatus.UPLOADING)
                .build();
        courseMedia.setCertificateTemplate(certificateTemplate);
        return courseMediaMapper.toResponse(courseMediaRepository.save(courseMedia));
    }

    // ─── Find ─────────────────────────────────────────────────

    public CourseMediaResponse findByCourseId(Long courseId)
    {
        return courseMediaMapper.toResponse(getCourseMedia(courseId));
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public CourseMediaResponse deleteThumbnail(Long courseId) throws  IOException
    {
        CourseMedia courseMedia = getCourseMedia(courseId);
        if(courseMedia.getThumbnail().getPublicId()!=null)
        {
            cloudinaryService.delete(courseMedia.getThumbnail().getPublicId(), MediaType.IMAGE);

        }
        courseMedia.setThumbnail(null);
        return courseMediaMapper.toResponse(
                courseMediaRepository.save(
                        courseMedia
                )
        );
    }

    @Transactional
    public CourseMediaResponse deletePromotionalLesson(Long courseId) throws  IOException
    {
        CourseMedia courseMedia = getCourseMedia(courseId);
        if(courseMedia.getPromotionalLesson().getPublicId()!=null)
        {
            cloudinaryService.delete(courseMedia.getPromotionalLesson().getPublicId(), MediaType.VIDEO);

        }
        courseMedia.setPromotionalLesson(null);
        return courseMediaMapper.toResponse(
                courseMediaRepository.save(
                        courseMedia
                )
        );
    }

    @Transactional
    public CourseMediaResponse deleteCertificateTemplate(Long courseId) throws  IOException
    {
        CourseMedia courseMedia = getCourseMedia(courseId);
        if(courseMedia.getCertificateTemplate().getPublicId()!=null)
        {
            cloudinaryService.delete(courseMedia.getCertificateTemplate().getPublicId(), MediaType.PDF);

        }
        courseMedia.setCertificateTemplate(null);
        return courseMediaMapper.toResponse(courseMediaRepository.save(courseMedia));
    }



    // ─── Helper ───────────────────────────────────────────────

    private CourseMedia getOrCreateCourseMedia(
            Long courseId
    ) {
        return courseMediaRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId
                )
                .orElseGet(() -> {

                    Course course =
                            getCourse(courseId);

                    CourseMedia media =
                            CourseMedia.builder()
                                    .course(course)
                                    .build();

                    return courseMediaRepository
                            .save(media);
                });
    }

    private CourseMedia getCourseMedia(
            Long courseId
    ) {

        return courseMediaRepository
                .findByCourseIdAndCourseDeletedAtIsNull(
                        courseId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course media not found."
                        )
                );
    }

    private Course getCourse(Long courseId) {

        return courseRepository
                .findByIdAndDeletedAtIsNull(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found."
                        )
                );
    }

    public String getCourseFolder(Long courseId)
    {

                return CloudinaryFolders.COURSE + "/" + courseId;
    }
}