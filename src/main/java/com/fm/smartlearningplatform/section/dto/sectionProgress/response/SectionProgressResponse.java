package com.fm.smartlearningplatform.section.dto.sectionProgress.response;



import java.time.LocalDateTime;

public record SectionProgressResponse(

        Long id,

        Long userId,

        Long sectionId,

        Integer completedLessons,

        Integer totalLessons,

        Boolean completed,

        LocalDateTime completedAt,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}