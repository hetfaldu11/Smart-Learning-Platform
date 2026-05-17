package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseRequirement;

public interface CourseRequirementsService {
    public  void save(CourseRequirement courseRequirement);

    public CourseRequirement findById(Long id);

     public void deleteById(Long id);
}
