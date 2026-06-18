package com.fm.smartlearningplatform.course.service;

import com.fm.smartlearningplatform.course.dto.coursePricing.request.CreateCoursePricingRequest;
import com.fm.smartlearningplatform.course.dto.coursePricing.request.UpdateCoursePricingRequest;
import com.fm.smartlearningplatform.course.dto.coursePricing.response.CoursePricingResponse;
import com.fm.smartlearningplatform.course.mapper.CoursePricingMapper;
import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.model.CoursePricing;
import com.fm.smartlearningplatform.course.model.Currency;
import com.fm.smartlearningplatform.course.repository.CoursePricingRepository;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.repository.CurrencyRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoursePricingService {

    private final CoursePricingRepository coursePricingRepository;
    private final CourseRepository courseRepository;
    private final CurrencyRepository currencyRepository;
    private final CoursePricingMapper coursePricingMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public CoursePricingResponse create(
            CreateCoursePricingRequest request
    ) {

        validateCoursePricingNotExist(
                request.courseId()
        );

        validateDiscountPrice(
                request.price(),
                request.discountPrice()
        );

        Course course = getCourse(
                request.courseId()
        );

        Currency currency = getCurrency(
                request.currencyId()
        );

        CoursePricing coursePricing =
                coursePricingMapper.toEntity(request);

        coursePricing.setCourse(course);
        coursePricing.setCurrency(currency);

        return coursePricingMapper.toResponse(
                coursePricingRepository.save(coursePricing)
        );
    }

    // ─── Find ─────────────────────────────────────────────────

    public CoursePricingResponse findByCourseId(
            Long courseId
    ) {

        return coursePricingMapper.toResponse(
                getCoursePricing(courseId)
        );
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public CoursePricingResponse update(
            Long courseId,
            UpdateCoursePricingRequest request
    ) {

        CoursePricing coursePricing =
                getCoursePricing(courseId);

        if (
                request.price() != null
                        && request.discountPrice() != null
        ) {

            validateDiscountPrice(
                    request.price(),
                    request.discountPrice()
            );

        } else if (request.discountPrice() != null) {

            validateDiscountPrice(
                    coursePricing.getPrice(),
                    request.discountPrice()
            );

        } else if (request.price() != null) {

            validateDiscountPrice(
                    request.price(),
                    coursePricing.getDiscountPrice()
            );
        }

        if (request.currencyId() != null) {

            Currency currency = getCurrency(
                    request.currencyId()
            );

            coursePricing.setCurrency(currency);
        }

        coursePricingMapper.update(
                request,
                coursePricing
        );

        return coursePricingMapper.toResponse(
                coursePricingRepository.save(coursePricing)
        );
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long courseId) {

        CoursePricing coursePricing =
                getCoursePricing(courseId);

        coursePricingRepository.delete(coursePricing);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsByCourseId(
            Long courseId
    ) {

        return coursePricingRepository
                .existsByCourseIdAndCourseDeletedAtIsNull(courseId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private CoursePricing getCoursePricing(
            Long courseId
    ) {

        return coursePricingRepository
                .findByCourseIdAndCourseDeletedAtIsNull(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course pricing not found."
                        )
                );
    }

    private Course getCourse(Long courseId) {

        return courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course not found."
                        )
                );
    }

    private Currency getCurrency(
            Long currencyId
    ) {

        return currencyRepository.findById(currencyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Currency not found."
                        )
                );
    }

    private void validateCoursePricingNotExist(
            Long courseId
    ) {

        if (
                coursePricingRepository
                        .existsByCourseIdAndCourseDeletedAtIsNull(courseId)
        ) {

            throw new DuplicateResourceException(
                    "Course pricing already exists."
            );
        }
    }

    private void validateDiscountPrice(
            java.math.BigDecimal price,
            java.math.BigDecimal discountPrice
    ) {

        if (
                discountPrice.compareTo(price) > 0
        ) {

            throw new IllegalArgumentException(
                    "Discount price cannot be greater than price."
            );
        }
    }
}