package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseMedia;

public interface CourseMediaService {

    void save(CourseMedia courseMedia);

    CourseMedia findById(Long id);

    void deleteById(Long id);
}