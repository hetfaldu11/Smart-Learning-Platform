package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseLearningOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLearningOutcomesRepository extends JpaRepository<CourseLearningOutcome, Long> {
}
