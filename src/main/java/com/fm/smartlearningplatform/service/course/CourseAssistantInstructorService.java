package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseAssistantInstructor;
import com.fm.smartlearningplatform.model.course.CourseAssistantInstructorId;
import org.springframework.stereotype.Service;


public interface CourseAssistantInstructorService {
    public void save(CourseAssistantInstructor courseAssistantInstructor);
    public CourseAssistantInstructor findById(CourseAssistantInstructorId courseAssistantInstructorId);
    public void deleteById(CourseAssistantInstructorId courseAssistantInstructorId);
}
