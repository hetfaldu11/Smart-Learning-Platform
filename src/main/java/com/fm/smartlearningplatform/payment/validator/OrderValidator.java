package com.fm.smartlearningplatform.payment.validator;

import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    public void validateCreateOrder(
            User user,
            Course course
    ) {

        validateCourse(course);

        validateOwnership(user, course);

    }

    private void validateCourse(Course course) {

    }

    private void validateOwnership(
            User user,
            Course course
    ) {

    }

}