package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseDetail;
import com.fm.smartlearningplatform.model.course.CoursePricing;
import com.fm.smartlearningplatform.repository.course.CourseDetailRepository;
import com.fm.smartlearningplatform.repository.course.CoursePricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursePricingImpl implements CoursePricingService{

    CoursePricingRepository coursePricingRepository;
    @Autowired
    public CoursePricingImpl(CoursePricingRepository coursePricingRepository)
    {
        this.coursePricingRepository= coursePricingRepository;
    }
    @Override
    public void save(CoursePricing coursePricing) {
        coursePricingRepository.save(coursePricing);
    }
    @Override
    public CoursePricing findById(Long id)
    {
        return coursePricingRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id)
    {
        coursePricingRepository.deleteById(id);
    }
}
