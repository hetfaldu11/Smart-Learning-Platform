package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseLearningOutcomes;
import com.fm.smartlearningplatform.model.course.CourseRequirements;

public interface CourseLearningOutcomesService {
    public  void save(CourseLearningOutcomes courseLearningOutcomes);

    public CourseLearningOutcomes findById(Long id);

    public void deleteById(Long id);
}
