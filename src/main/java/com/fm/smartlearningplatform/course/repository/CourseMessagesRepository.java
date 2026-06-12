package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMessagesRepository extends JpaRepository<CourseMessage, Long> {
}
