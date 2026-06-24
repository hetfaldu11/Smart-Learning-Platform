package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonProgressRepository extends JpaRepository<LessonProgress,Long> {
}
