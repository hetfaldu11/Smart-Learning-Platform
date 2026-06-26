package com.fm.smartlearningplatform.lesson.service;


import com.fm.smartlearningplatform.cloudinary.constant.CloudinaryFolders;
import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.cloudinary.service.CloudinaryService;
import com.fm.smartlearningplatform.cloudinary.validation.FileValidation;
import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.common.service.FileService;
import com.fm.smartlearningplatform.exceptionhandler.exception.BadRequestException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.lesson.dto.videoLesson.response.VideoLessonResponse;

import com.fm.smartlearningplatform.lesson.mapper.VideoLessonMapper;
import com.fm.smartlearningplatform.lesson.model.Lesson;
import com.fm.smartlearningplatform.lesson.model.LessonType;
import com.fm.smartlearningplatform.lesson.model.VideoLesson;
import com.fm.smartlearningplatform.lesson.repository.LessonRepository;
import com.fm.smartlearningplatform.lesson.repository.VideoLessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoLessonService {

    private final VideoLessonRepository videoLessonRepository;

    private final LessonRepository lessonRepository;

    private final VideoLessonMapper videoLessonMapper;

    private final CloudinaryService cloudinaryService;

    private final FileValidation fileValidation;

    private final FileService fileService;

    // ─── Upload Video ──────────────────────────────────────────

    @Transactional
    public VideoLessonResponse uploadVideo(Long lessonId, MultipartFile file) throws IOException {

        Lesson lesson = getLesson(lessonId);

        validateLessonType(lesson);

        fileValidation.validateVideo(file);

        VideoLesson videoLesson = getOrCreateVideoLesson(lesson);

        String folder = getLessonFolder(lessonId);

        CloudinaryUploadResponse response = cloudinaryService.uploadVideo(file, folder, "video");

        File oldVideo = videoLesson.getVideo();

        fileService.softDelete(oldVideo);

        File video = fileService.createFile(file, response, StorageProvider.CLOUDINARY, FileStatus.UPLOADING);

        videoLesson.setVideo(video);

        /*
         * Update lesson duration from Cloudinary metadata.
         * Replace getDurationSeconds() with your actual method.
         */
        lesson.setDurationSeconds(response.durationSeconds());

        lessonRepository.save(lesson);

        return videoLessonMapper.toResponse(videoLessonRepository.save(videoLesson));
    }

    // ─── Upload Thumbnail ─────────────────────────────────────

    @Transactional
    public VideoLessonResponse uploadThumbnail(Long lessonId, MultipartFile file) throws IOException {

        Lesson lesson = getLesson(lessonId);

        validateLessonType(lesson);

        fileValidation.validateImage(file);

        VideoLesson videoLesson = getOrCreateVideoLesson(lesson);

        String folder = getLessonFolder(lessonId);

        CloudinaryUploadResponse response = cloudinaryService.uploadImage(file, folder, "thumbnail");

        File oldThumbnail = videoLesson.getThumbnail();

        fileService.softDelete(oldThumbnail);

        File thumbnail = fileService.createFile(file, response, StorageProvider.CLOUDINARY, FileStatus.UPLOADING);

        videoLesson.setThumbnail(thumbnail);

        return videoLessonMapper.toResponse(videoLessonRepository.save(videoLesson));
    }
    // ─── Find ─────────────────────────────────────────────────

    public VideoLessonResponse findByLessonId(Long lessonId) {

        return videoLessonMapper.toResponse(getVideoLesson(lessonId));
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void deleteVideoLesson(Long lessonId) throws IOException {

        VideoLesson videoLesson = getVideoLesson(lessonId);

        fileService.softDelete(videoLesson.getVideo());

        if (videoLesson.getThumbnail() != null) {

            fileService.softDelete(videoLesson.getThumbnail());
        }

        Lesson lesson = videoLesson.getLesson();

        lesson.setDeletedAt(LocalDateTime.now());

        lessonRepository.save(lesson);

        videoLessonRepository.delete(videoLesson);
    }

    @Transactional
    public VideoLessonResponse deleteThumbnail(Long lessonId) throws IOException {

        VideoLesson videoLesson = getVideoLesson(lessonId);

        fileService.softDelete(videoLesson.getThumbnail());

        videoLesson.setThumbnail(null);

        return videoLessonMapper.toResponse(videoLessonRepository.save(videoLesson));
    }

    // ─── Helper ───────────────────────────────────────────────

    private VideoLesson getOrCreateVideoLesson(Lesson lesson) {

        return videoLessonRepository.findByLessonId(lesson.getId()).orElseGet(() -> {

            VideoLesson videoLesson = VideoLesson.builder().lesson(lesson).build();

            return videoLessonRepository.save(videoLesson);
        });
    }

    private VideoLesson getVideoLesson(Long lessonId) {

        return videoLessonRepository.findByLessonId(lessonId).orElseThrow(() -> new ResourceNotFoundException("Video lesson not found."));
    }

    private Lesson getLesson(Long lessonId) {

        return lessonRepository.findByIdAndDeletedAtIsNull(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not found."));
    }

    private void validateLessonType(Lesson lesson) {

        if (lesson.getType() != LessonType.VIDEO) {

            throw new BadRequestException("Only video lessons can upload videos.");
        }
    }

    private String getLessonFolder(Long lessonId) {

        Lesson lesson = getLesson(lessonId);
        return CloudinaryFolders.LESSON_VIDEOS + "/course-" + lesson.getSection().getCourse().getId() + "/lesson-" + lesson.getId();
    }
}