package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseLanguage;
import com.fm.smartlearningplatform.repository.course.CourseLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseLanguageServiceImpl implements CourseLanguageService {

    private final CourseLanguageRepository courseLanguageRepository;

    @Autowired
    public CourseLanguageServiceImpl(CourseLanguageRepository courseLanguageRepository) {
        this.courseLanguageRepository = courseLanguageRepository;
    }

    @Override
    public void save(CourseLanguage courseLanguage) {
        courseLanguageRepository.save(courseLanguage);
    }

    @Override
    public CourseLanguage findById(Long id) {
        return courseLanguageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseLanguageRepository.deleteById(id);
    }
}