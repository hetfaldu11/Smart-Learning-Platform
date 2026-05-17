package com.fm.smartlearningplatform.repository.course;

import com.fm.smartlearningplatform.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
