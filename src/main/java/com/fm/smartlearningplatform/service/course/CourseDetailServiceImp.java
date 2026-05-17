package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseDetail;
import com.fm.smartlearningplatform.repository.course.CourseDetailRepository;
import com.fm.smartlearningplatform.repository.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseDetailServiceImp implements  CourseDetailService {

    CourseDetailRepository courseDetailRepository;
    @Autowired
    public CourseDetailServiceImp(CourseDetailRepository courseDetailRepository)
    {
        this.courseDetailRepository= courseDetailRepository;
    }
    @Override
    public void save(CourseDetail courseDetail) {
        courseDetailRepository.save(courseDetail);
    }
    @Override
    public CourseDetail findById(Long id)
    {
        return courseDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id)
    {
        courseDetailRepository.deleteById(id);
    }
}