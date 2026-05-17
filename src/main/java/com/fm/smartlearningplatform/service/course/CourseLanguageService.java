package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseLanguage;

public interface CourseLanguageService {
    public void save(CourseLanguage courseLanguage);
    public CourseLanguage findById(Long id);
    public void deleteById(Long id);
}
