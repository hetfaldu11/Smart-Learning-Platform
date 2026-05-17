package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.Course;

public interface CourseService {
    public void save(Course course);
    public Course findById(Long id);
    public void deleteById(Long id);
}
