package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseLearningOutcome;

public interface CourseLearningOutcomesService {
    public  void save(CourseLearningOutcome courseLearningOutcome);

    public CourseLearningOutcome findById(Long id);

    public void deleteById(Long id);
}
