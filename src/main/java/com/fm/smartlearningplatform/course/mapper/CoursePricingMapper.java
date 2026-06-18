package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.coursePricing.request.CreateCoursePricingRequest;
import com.fm.smartlearningplatform.course.dto.coursePricing.request.UpdateCoursePricingRequest;
import com.fm.smartlearningplatform.course.dto.coursePricing.response.CoursePricingResponse;
import com.fm.smartlearningplatform.course.model.CoursePricing;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CoursePricingMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "currency", ignore = true)

//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
    CoursePricing toEntity(
            CreateCoursePricingRequest request
    );



    @Mapping(target = "courseId", source = "course.id")

    @Mapping(target = "currencyId", source = "currency.id")
    @Mapping(target = "currencyName", source = "currency.name")
    @Mapping(target = "currencyCode", source = "currency.code")
    CoursePricingResponse toResponse(
            CoursePricing coursePricing
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "currency", ignore = true)
    void update(
            UpdateCoursePricingRequest request,
            @MappingTarget CoursePricing coursePricing
    );

}