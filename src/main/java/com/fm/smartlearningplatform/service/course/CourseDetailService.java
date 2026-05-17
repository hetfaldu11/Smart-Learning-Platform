package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.Course;
import com.fm.smartlearningplatform.model.course.CourseDetail;

public interface CourseDetailService {
    public void save(CourseDetail courseDetail);
    public CourseDetail findById(Long id);
    public void deleteById(Long id);
}