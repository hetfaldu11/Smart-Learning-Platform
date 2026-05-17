package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseRequirements;
import com.fm.smartlearningplatform.model.course.CourseSupport;
import com.fm.smartlearningplatform.repository.course.CourseRequirementsRepository;
import com.fm.smartlearningplatform.repository.course.CourseSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseRequirementsServiceImpl implements  CourseRequirementsService{

    private final CourseRequirementsRepository courseRequirementsRepository;

    @Autowired
    public CourseRequirementsServiceImpl(CourseRequirementsRepository courseRequirementsRepository) {
        this.courseRequirementsRepository = courseRequirementsRepository;
    }

    @Override
    public void save(CourseRequirements courseRequirements) {
        courseRequirementsRepository.save(courseRequirements);
    }

    @Override
    public CourseRequirements findById(Long id) {
        return courseRequirementsRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseRequirementsRepository.deleteById(id);
    }
}
