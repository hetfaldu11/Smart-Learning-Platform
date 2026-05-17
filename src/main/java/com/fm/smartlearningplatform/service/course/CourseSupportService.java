package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseSupport;

public interface CourseSupportService {

    void save(CourseSupport courseSupport);

    CourseSupport findById(Long id);

    void deleteById(Long id);
}
