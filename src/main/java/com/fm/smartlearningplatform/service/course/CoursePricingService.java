package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseDetail;
import com.fm.smartlearningplatform.model.course.CoursePricing;

public interface CoursePricingService {

    public void save(CoursePricing coursePricing);
    public CoursePricing findById(Long id);
    public void deleteById(Long id);
}
