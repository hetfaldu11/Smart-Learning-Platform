package com.fm.smartlearningplatform.payment.validator;

import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.course.repository.EnrollmentRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public void validateCreateOrder(Long userId, Long courseId) {
        validateCourse(courseId);
        validateOwnership(userId, courseId);

    }

    private void validateCourse(Long courseId) {
      Course course=  courseRepository.findByIdAndDeletedAtIsNull(courseId).orElseThrow(() -> new ResourceNotFoundException("course not exists"));
//        if (course.getCourseStatus()!="published") {
//            throw new IllegalStateException("Course is not available.");
//        }

    }

    private void validateOwnership(Long userId, Long courseId) {
        if (enrollmentRepository.existsByUserIdAndCourseIdAndCourseDeletedAtIsNull(userId, courseId)) {
            throw new DuplicateResourceException("you have already enrolled this course");
        }
    }

}