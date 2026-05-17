package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseAssistantInstructor;
import com.fm.smartlearningplatform.repository.course.CourseAssistantInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseAssistantInstructorServiceImpl implements  CourseAssistantInstructorService{
    CourseAssistantInstructorRepository courseAssistantInstructorRepository;

    @Autowired
    public CourseAssistantInstructorServiceImpl (CourseAssistantInstructorRepository courseAssistantInstructorRepository)
    {
        this.courseAssistantInstructorRepository= courseAssistantInstructorRepository;
    }
    @Override

    public void save(CourseAssistantInstructor courseAssistantInstructor)
    {
        courseAssistantInstructorRepository.save(courseAssistantInstructor);
    }

    @Override
    public CourseAssistantInstructor findById(Long id)
    {
        return courseAssistantInstructorRepository.findById( id ).orElse(null);
    }
    @Override
    public void deleteById(Long id)
    {
         courseAssistantInstructorRepository.deleteById(id );
    }
}
