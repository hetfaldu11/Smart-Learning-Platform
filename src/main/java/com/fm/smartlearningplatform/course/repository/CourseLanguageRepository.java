package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLanguageRepository extends JpaRepository<CourseLanguage, Long> {
}
