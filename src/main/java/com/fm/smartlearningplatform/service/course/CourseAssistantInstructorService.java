package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseAssistantInstructor;
import org.springframework.stereotype.Service;


public interface CourseAssistantInstructorService {
    public void save(CourseAssistantInstructor courseAssistantInstructor);
    public CourseAssistantInstructor findById(Long id);
    public void deleteById(Long id);
}
