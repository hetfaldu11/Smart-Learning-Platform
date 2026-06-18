package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request.CreateAssistantInstructorRoleRequest;
import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.request.UpdateAssistantInstructorRoleRequest;
import com.fm.smartlearningplatform.course.dto.assistantInstructorRole.response.AssistantInstructorRoleResponse;
import com.fm.smartlearningplatform.course.model.AssistantInstructorRole;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AssistantInstructorRoleMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "deletedAt", ignore = true)

    AssistantInstructorRole toEntity(CreateAssistantInstructorRoleRequest request);



    AssistantInstructorRoleResponse toResponse(AssistantInstructorRole assistantInstructorRole);



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)

    void update(
            UpdateAssistantInstructorRoleRequest request,
            @MappingTarget AssistantInstructorRole assistantInstructorRole
    );

}