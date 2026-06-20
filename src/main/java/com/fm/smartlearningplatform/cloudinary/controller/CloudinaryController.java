package com.fm.smartlearningplatform.cloudinary.controller;



import com.fm.smartlearningplatform.cloudinary.service.CloudinaryService;
import com.fm.smartlearningplatform.course.dto.courseMedia.response.CourseMediaResponse;
import com.fm.smartlearningplatform.course.service.CourseMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;
    private  final CourseMediaService courseMediaService;

//    @PostMapping("/upload")
//    public String upload(
//            @RequestParam("file")
//            MultipartFile file
//    ) throws Exception {
//
//        return cloudinaryService.uploadImage(file);
//    }
    @PostMapping(
            value = "/{courseId}/thumbnail",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CourseMediaResponse uploadThumbnail(
            @PathVariable Long courseId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return courseMediaService
                .uploadThumbnail(
                        courseId,
                        file
                );
    }
}