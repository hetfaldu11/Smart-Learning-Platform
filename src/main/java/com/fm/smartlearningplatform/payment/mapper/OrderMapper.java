package com.fm.smartlearningplatform.payment.mapper;

import com.fm.smartlearningplatform.payment.dto.response.OrderResponse;
import com.fm.smartlearningplatform.payment.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = OrderItemMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {

    OrderResponse toResponse(Order order);

}
