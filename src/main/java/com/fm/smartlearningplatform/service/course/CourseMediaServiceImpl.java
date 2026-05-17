package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseMedia;
import com.fm.smartlearningplatform.repository.course.CourseMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMediaServiceImpl implements CourseMediaService {

    private final CourseMediaRepository courseMediaRepository;

    @Autowired
    public CourseMediaServiceImpl(CourseMediaRepository courseMediaRepository) {
        this.courseMediaRepository = courseMediaRepository;
    }

    @Override
    public void save(CourseMedia courseMedia) {
        courseMediaRepository.save(courseMedia);
    }

    @Override
    public CourseMedia findById(Long id) {
        return courseMediaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseMediaRepository.deleteById(id);
    }
}