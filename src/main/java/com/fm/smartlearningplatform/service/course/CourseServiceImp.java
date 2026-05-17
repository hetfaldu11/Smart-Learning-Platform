package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.Course;
import com.fm.smartlearningplatform.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CourseServiceImp implements  CourseService{

    CourseRepository courseRepository;
    @Autowired
    public CourseServiceImp(CourseRepository courseRepository)
    {
        this.courseRepository=courseRepository;
    }
    @Override
    public void save(Course course)
    {
        courseRepository.save(course);
    }
    @Override
    public Course findById(Long id)
    {
        return courseRepository.findById(id).orElse(null);
    }
    @Override
    public void deleteById(Long id)
    {
        courseRepository.deleteById(id);
    }
}
