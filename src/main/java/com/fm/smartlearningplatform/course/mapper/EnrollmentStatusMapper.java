package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.enrollmentStatus.request.CreateEnrollmentStatusRequest;
import com.fm.smartlearningplatform.course.dto.enrollmentStatus.request.UpdateEnrollmentStatusRequest;
import com.fm.smartlearningplatform.course.dto.enrollmentStatus.response.EnrollmentStatusResponse;
import com.fm.smartlearningplatform.course.model.EnrollmentStatus;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EnrollmentStatusMapper {

    @Mapping(target = "id", ignore = true)
    EnrollmentStatus toEntity(
            CreateEnrollmentStatusRequest request
    );


    EnrollmentStatusResponse toResponse(
            EnrollmentStatus enrollmentStatus
    );


    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    void update(
            UpdateEnrollmentStatusRequest request,
            @MappingTarget EnrollmentStatus enrollmentStatus
    );

}