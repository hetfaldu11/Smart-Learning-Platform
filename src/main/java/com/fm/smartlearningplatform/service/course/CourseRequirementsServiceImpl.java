package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseRequirement;
import com.fm.smartlearningplatform.repository.course.CourseRequirementsRepository;
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
    public void save(CourseRequirement courseRequirement) {
        courseRequirementsRepository.save(courseRequirement);
    }

    @Override
    public CourseRequirement findById(Long id) {
        return courseRequirementsRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseRequirementsRepository.deleteById(id);
    }
}
