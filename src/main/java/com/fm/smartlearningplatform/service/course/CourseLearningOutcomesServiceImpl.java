package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseLearningOutcome;
import com.fm.smartlearningplatform.repository.course.CourseLearningOutcomesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseLearningOutcomesServiceImpl implements CourseLearningOutcomesService{
    private final CourseLearningOutcomesRepository courseLearningOutcomesRepository;

    @Autowired
    public CourseLearningOutcomesServiceImpl(CourseLearningOutcomesRepository courseLearningOutcomesRepository) {
        this.courseLearningOutcomesRepository = courseLearningOutcomesRepository;
    }

    @Override
    public void save(CourseLearningOutcome courseLearningOutcome) {
        courseLearningOutcomesRepository.save(courseLearningOutcome);
    }

    @Override
    public CourseLearningOutcome findById(Long id) {
        return courseLearningOutcomesRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseLearningOutcomesRepository.deleteById(id);
    }
}
