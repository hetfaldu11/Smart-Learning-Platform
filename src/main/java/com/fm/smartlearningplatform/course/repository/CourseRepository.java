package com.fm.smartlearningplatform.course.repository;


import com.fm.smartlearningplatform.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
