package com.fm.smartlearningplatform.course.mapper;

import com.fm.smartlearningplatform.course.dto.currency.request.CreateCurrencyRequest;
import com.fm.smartlearningplatform.course.dto.currency.request.UpdateCurrencyRequest;
import com.fm.smartlearningplatform.course.dto.currency.response.CurrencyResponse;
import com.fm.smartlearningplatform.course.model.Currency;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CurrencyMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Currency toEntity(
            CreateCurrencyRequest request
    );



    CurrencyResponse toResponse(
            Currency currency
    );



    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void update(
            UpdateCurrencyRequest request,
            @MappingTarget Currency currency
    );

}