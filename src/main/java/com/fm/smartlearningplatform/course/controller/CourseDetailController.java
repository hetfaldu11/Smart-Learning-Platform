package com.fm.smartlearningplatform.course.controller;

import com.fm.smartlearningplatform.course.dto.courseDetail.request.CreateCourseDetailRequest;
import com.fm.smartlearningplatform.course.dto.courseDetail.request.UpdateCourseDetailRequest;
import com.fm.smartlearningplatform.course.dto.courseDetail.response.CourseDetailResponse;
import com.fm.smartlearningplatform.course.service.CourseDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course-details")
@RequiredArgsConstructor
public class CourseDetailController {

    private final CourseDetailService courseDetailService;

    // ─── Create ───────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CourseDetailResponse>
    createCourseDetail(
            @Valid
            @RequestBody
            CreateCourseDetailRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        courseDetailService.create(
                                request
                        )
                );
    }

    // ─── Find ─────────────────────────────────────────────────

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailResponse>
    getCourseDetailByCourseId(
            @PathVariable Long courseId
    ) {

        return ResponseEntity.ok(
                courseDetailService.findByCourseId(
                        courseId
                )
        );
    }

//    @GetMapping
//    public ResponseEntity<Page<CourseDetailResponse>> getCourseDetails(
//            @PageableDefault(
//                    size = 10,
//                    sort = "createdAt"
//            )
//            Pageable pageable
//    ) {
//        return ResponseEntity.ok(
//                courseDetailService.findAll(pageable)
//        );
//    }

    // ─── Update ───────────────────────────────────────────────

    @PutMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailResponse>
    updateCourseDetailByCourseId(
            @PathVariable Long courseId,

            @Valid
            @RequestBody
            UpdateCourseDetailRequest request
    ) {

        return ResponseEntity.ok(
                courseDetailService.update(
                        courseId,
                        request
                )
        );
    }
}