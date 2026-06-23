package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.cloudinary.constant.CloudinaryFolders;
import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.cloudinary.enums.MediaType;
import com.fm.smartlearningplatform.cloudinary.service.CloudinaryService;
import com.fm.smartlearningplatform.cloudinary.validation.FileValidation;
import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.FileType;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import com.fm.smartlearningplatform.common.mapper.FileMapper;
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
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseMediaService {

    private final CourseMediaRepository courseMediaRepository;

    private final CourseRepository courseRepository;

    private final CourseMediaMapper courseMediaMapper;

    private final CloudinaryService cloudinaryService;

    private final FileValidation fileValidation;

    private final FileMapper fileMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CourseMediaResponse uploadThumbnail(Long courseId, MultipartFile file) throws IOException {
        String folder = getCourseFolder(courseId);

        String publicId = "thumbnail";
        fileValidation.validateImage(file);

        CourseMedia courseMedia = getOrCreateCourseMedia(courseId);
        CloudinaryUploadResponse response = cloudinaryService.uploadImage(file, folder, publicId);

        File oldFile = courseMedia.getThumbnail();

        if (oldFile != null) {
            oldFile.setDeletedAt(LocalDateTime.now());
            oldFile.setStatus(FileStatus.DELETED);
        }

        File thumbnail = fileMapper.toEntity(file,response,StorageProvider.CLOUDINARY,FileStatus.UPLOADING);
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

        File oldFile = courseMedia.getPromotionalLesson();

        if (oldFile != null) {
            oldFile.setDeletedAt(LocalDateTime.now());
            oldFile.setStatus(FileStatus.DELETED);
        }
        File promotionalLesson = fileMapper.toEntity(file,response,StorageProvider.CLOUDINARY,FileStatus.UPLOADING);
        courseMedia.setPromotionalLesson(promotionalLesson);
        return courseMediaMapper.toResponse(courseMediaRepository.save(courseMedia));
    }

    @Transactional
    public CourseMediaResponse uploadCertificateTemplate(Long courseId, MultipartFile file) throws IOException {
        String folder = getCourseFolder(courseId);
        String publicId = "certificate-template";
        fileValidation.validatePdf(file);

        CourseMedia courseMedia = getOrCreateCourseMedia(courseId);

        CloudinaryUploadResponse response = cloudinaryService.uploadPdf(file, folder, publicId);
        File oldFile = courseMedia.getCertificateTemplate();

        if (oldFile != null) {
            oldFile.setDeletedAt(LocalDateTime.now());
            oldFile.setStatus(FileStatus.DELETED);
        }
        File certificateTemplate = fileMapper.toEntity(file,response,StorageProvider.CLOUDINARY,FileStatus.UPLOADING);
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