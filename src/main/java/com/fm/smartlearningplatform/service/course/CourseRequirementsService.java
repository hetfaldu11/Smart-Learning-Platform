package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseRequirements;
import com.fm.smartlearningplatform.model.course.CourseSupport;

public interface CourseRequirementsService {
    public  void save(CourseRequirements courseRequirements);

    public CourseRequirements findById(Long id);

     public void deleteById(Long id);
}
