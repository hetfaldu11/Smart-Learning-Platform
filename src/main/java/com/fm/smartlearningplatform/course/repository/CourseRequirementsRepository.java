package com.fm.smartlearningplatform.course.repository;


import com.fm.smartlearningplatform.course.model.CourseRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRequirementsRepository extends JpaRepository<CourseRequirement, Long> {
}
