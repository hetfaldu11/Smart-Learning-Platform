package com.fm.smartlearningplatform.repository.course;

import com.fm.smartlearningplatform.model.course.CourseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseMessagesRepository extends JpaRepository<CourseMessage, Long> {
}
