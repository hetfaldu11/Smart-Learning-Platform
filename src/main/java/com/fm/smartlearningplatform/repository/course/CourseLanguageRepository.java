package com.fm.smartlearningplatform.repository.course;

import com.fm.smartlearningplatform.model.course.CourseLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseLanguageRepository extends JpaRepository<CourseLanguage, Long> {
}
