package com.fm.smartlearningplatform.payment.mapper;

import com.fm.smartlearningplatform.payment.dto.response.OrderItemResponse;
import com.fm.smartlearningplatform.payment.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderItemMapper {

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")
    OrderItemResponse toResponse(OrderItem orderItem);

}
