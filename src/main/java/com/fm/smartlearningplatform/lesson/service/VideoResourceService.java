package com.fm.smartlearningplatform.lesson.service;

import com.fm.smartlearningplatform.cloudinary.constant.CloudinaryFolders;
import com.fm.smartlearningplatform.cloudinary.dto.CloudinaryUploadResponse;
import com.fm.smartlearningplatform.cloudinary.service.CloudinaryService;
import com.fm.smartlearningplatform.cloudinary.validation.FileValidation;
import com.fm.smartlearningplatform.common.enums.FileStatus;
import com.fm.smartlearningplatform.common.enums.StorageProvider;
import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.common.service.FileService;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.lesson.dto.videoResource.request.CreateVideoResourceRequest;
import com.fm.smartlearningplatform.lesson.dto.videoResource.response.VideoResourceResponse;
import com.fm.smartlearningplatform.lesson.mapper.VideoResourceMapper;
import com.fm.smartlearningplatform.lesson.model.VideoLesson;
import com.fm.smartlearningplatform.lesson.model.VideoResource;
import com.fm.smartlearningplatform.lesson.repository.VideoLessonRepository;
import com.fm.smartlearningplatform.lesson.repository.VideoResourceRepository;
import com.fm.smartlearningplatform.lesson.dto.videoResource.request.UpdateVideoResourceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoResourceService {

    private final VideoResourceRepository videoResourceRepository;

    private final VideoLessonRepository videoLessonRepository;

    private final VideoResourceMapper videoResourceMapper;

    private final CloudinaryService cloudinaryService;

    private final FileValidation fileValidation;

    private final FileService fileService;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public VideoResourceResponse create(CreateVideoResourceRequest request, MultipartFile file) throws IOException {

        VideoLesson videoLesson = getVideoLesson(request.videoLessonId());

        validatePositionNotExist(request.videoLessonId(), request.position());

        fileValidation.validatePdf(file);

        String folder = getResourceFolder(request.videoLessonId());

        CloudinaryUploadResponse response = cloudinaryService.uploadPdf(file, folder, "resource-" + request.position());

        File resourceFile = fileService.createFile(file, response, StorageProvider.CLOUDINARY, FileStatus.UPLOADING);

        VideoResource videoResource = videoResourceMapper.toEntity(request);

        videoResource.setVideoLesson(videoLesson);

        videoResource.setFile(resourceFile);

        return videoResourceMapper.toResponse(videoResourceRepository.save(videoResource));
    }
    // ─── Find ─────────────────────────────────────────────────

    public VideoResourceResponse findById(Long videoResourceId) {

        return videoResourceMapper.toResponse(getVideoResource(videoResourceId));
    }

    public Page<VideoResourceResponse> findByVideoLessonId(Long videoLessonId, Pageable pageable) {

        getVideoLesson(videoLessonId);

        return videoResourceRepository.findByVideoLessonIdOrderByPositionAsc(videoLessonId, pageable).map(videoResourceMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public VideoResourceResponse update(Long videoResourceId, UpdateVideoResourceRequest request) {

        VideoResource videoResource = getVideoResource(videoResourceId);

        if (request.position() != null && !request.position().equals(videoResource.getPosition())) {

            validatePositionForUpdate(videoResource.getVideoLesson().getId(), request.position(), videoResourceId);
        }

        videoResourceMapper.update(request, videoResource);

        return videoResourceMapper.toResponse(videoResourceRepository.save(videoResource));
    }
    // ─── Replace File ───────────────────────────────────────────

    @Transactional
    public VideoResourceResponse replaceFile(Long videoResourceId, MultipartFile file) throws IOException {

        VideoResource videoResource = getVideoResource(videoResourceId);

        fileValidation.validatePdf(file);

        String folder = getResourceFolder(videoResource.getVideoLesson().getId());

        CloudinaryUploadResponse response = cloudinaryService.uploadPdf(file, folder, "resource-" + videoResource.getPosition());

        File oldFile = videoResource.getFile();

        if (oldFile != null) {

            fileService.softDelete(oldFile);
        }

        File newFile = fileService.createFile(file, response, StorageProvider.CLOUDINARY, FileStatus.UPLOADING);

        videoResource.setFile(newFile);

        return videoResourceMapper.toResponse(videoResourceRepository.save(videoResource));
    }

// ─── Delete ─────────────────────────────────────────────────

    @Transactional
    public void delete(Long videoResourceId) throws IOException {

        VideoResource videoResource = getVideoResource(videoResourceId);

        fileService.softDelete(videoResource.getFile());

        videoResourceRepository.delete(videoResource);
    }

// ─── Helper ─────────────────────────────────────────────────

    private VideoResource getVideoResource(Long videoResourceId) {

        return videoResourceRepository.findById(videoResourceId).orElseThrow(() -> new ResourceNotFoundException("Video resource not found."));
    }

    private VideoLesson getVideoLesson(Long videoLessonId) {

        return videoLessonRepository.findById(videoLessonId).orElseThrow(() -> new ResourceNotFoundException("Video lesson not found."));
    }

    private void validatePositionNotExist(Long videoLessonId, Integer position) {

        if (videoResourceRepository.existsByVideoLessonIdAndPosition(videoLessonId, position)) {

            throw new DuplicateResourceException("Resource position already exists.");
        }
    }

    private void validatePositionForUpdate(Long videoLessonId, Integer position, Long videoResourceId) {

        if (videoResourceRepository.existsByVideoLessonIdAndPositionAndIdNot(videoLessonId, position, videoResourceId)) {

            throw new DuplicateResourceException("Resource position already exists.");
        }
    }

    private String getResourceFolder(Long videoLessonId) {

        return CloudinaryFolders.LESSON_PDFS + "/" + videoLessonId;
    }
}