package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseMedia.response.CourseMediaResponse;
import com.fm.smartlearningplatform.course.service.CourseMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/course-media")
@RequiredArgsConstructor
public class CourseMediaController {

    private final CourseMediaService courseMediaService;

    // ─── Find ───────────────────────────────────────────────

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseMediaResponse> findByCourseId(
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                courseMediaService.findByCourseId(
                        courseId
                )
        );
    }

    // ─── Thumbnail ──────────────────────────────────────────

    @PostMapping(
            value = "/{courseId}/thumbnail",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CourseMediaResponse> uploadThumbnail(
            @PathVariable Long courseId,

            @RequestParam("file")
            MultipartFile file
    ) throws IOException {

        return ResponseEntity.ok(
                courseMediaService.uploadThumbnail(
                        courseId,
                        file
                )
        );
    }

    @DeleteMapping("/{courseId}/thumbnail")
    public ResponseEntity<CourseMediaResponse> deleteThumbnail(
            @PathVariable Long courseId
    ) throws IOException {

        return ResponseEntity.ok(
                courseMediaService.deleteThumbnail(
                        courseId
                )
        );
    }

    // ─── Promotional Lesson ────────────────────────────────

    @PostMapping(
            value = "/{courseId}/promotional-lesson",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CourseMediaResponse> uploadPromotionalLesson(
            @PathVariable Long courseId,

            @RequestParam("file")
            MultipartFile file
    ) throws IOException {

        return ResponseEntity.ok(
                courseMediaService.uploadPromotionalLesson(
                        courseId,
                        file
                )
        );
    }

    @DeleteMapping("/{courseId}/promotional-lesson")
    public ResponseEntity<CourseMediaResponse> deletePromotionalLesson(
            @PathVariable Long courseId
    ) throws IOException {

        return ResponseEntity.ok(
                courseMediaService.deletePromotionalLesson(
                        courseId
                )
        );
    }

    // ─── Certificate Template ──────────────────────────────

    @PostMapping(
            value = "/{courseId}/certificate-template",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CourseMediaResponse> uploadCertificateTemplate(
            @PathVariable Long courseId,

            @RequestParam("file")
            MultipartFile file
    ) throws IOException {

        return ResponseEntity.ok(
                courseMediaService.uploadCertificateTemplate(
                        courseId,
                        file
                )
        );
    }

    @DeleteMapping("/{courseId}/certificate-template")
    public ResponseEntity<CourseMediaResponse> deleteCertificateTemplate(
            @PathVariable Long courseId
    ) throws IOException {

        return ResponseEntity.ok(
                courseMediaService.deleteCertificateTemplate(
                        courseId
                )
        );
    }
}