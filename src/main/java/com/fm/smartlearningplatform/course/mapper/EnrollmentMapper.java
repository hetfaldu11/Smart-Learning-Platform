package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.enrollment.request.CreateEnrollmentRequest;
import com.fm.smartlearningplatform.course.dto.enrollment.request.UpdateEnrollmentRequest;
import com.fm.smartlearningplatform.course.dto.enrollment.response.EnrollmentResponse;
import com.fm.smartlearningplatform.course.model.Enrollment;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EnrollmentMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "enrollmentStatus", ignore = true)

    @Mapping(target = "completedAt", ignore = true)


    Enrollment toEntity(
            CreateEnrollmentRequest request
    );



    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.email")

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")

    @Mapping(
            target = "enrollmentStatusId",
            source = "enrollmentStatus.id"
    )
    @Mapping(
            target = "enrollmentStatusName",
            source = "enrollmentStatus.name"
    )
    EnrollmentResponse toResponse(
            Enrollment enrollment
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "enrollmentStatus", ignore = true)


    void update(
            UpdateEnrollmentRequest request,
            @MappingTarget Enrollment enrollment
    );

}