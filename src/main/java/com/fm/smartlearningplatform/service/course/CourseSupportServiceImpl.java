package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseSupport;
import com.fm.smartlearningplatform.repository.course.CourseSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseSupportServiceImpl implements CourseSupportService {

    private final CourseSupportRepository courseSupportRepository;

    @Autowired
    public CourseSupportServiceImpl(CourseSupportRepository courseSupportRepository) {
        this.courseSupportRepository = courseSupportRepository;
    }

    @Override
    public void save(CourseSupport courseSupport) {
        courseSupportRepository.save(courseSupport);
    }

    @Override
    public CourseSupport findById(Long id) {
        return courseSupportRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseSupportRepository.deleteById(id);
    }
}