package com.fm.smartlearningplatform.lesson.dto.videoResource.response;


import com.fm.smartlearningplatform.common.dto.response.FileResponse;

public record VideoResourceResponse(

        Long id,

        Long lessonId,
        Long videoLessonId,

        String lessonTitle,

        Integer position,

        String displayName,

        FileResponse file

) {
}
