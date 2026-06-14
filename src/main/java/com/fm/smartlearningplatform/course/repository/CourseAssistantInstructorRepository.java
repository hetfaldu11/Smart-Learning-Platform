package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseAssistantInstructor;
import com.fm.smartlearningplatform.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseAssistantInstructorRepository
        extends JpaRepository<CourseAssistantInstructor, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseAssistantInstructor>
    findById(Long id);

    List<CourseAssistantInstructor>
    findByCourseId(Long courseId);

    Page<CourseAssistantInstructor>
    findByCourseId(
            Long courseId,
            Pageable pageable
    );

    List<CourseAssistantInstructor>
    findByInstructorId(Long instructorId);

    Page<CourseAssistantInstructor>
    findByInstructorId(
            Long instructorId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Role Filtering
     |--------------------------------------------------------------------------
     */

    List<CourseAssistantInstructor>
    findByAssistantInstructorRoleId(
            Long roleId
    );

    Page<CourseAssistantInstructor>
    findByAssistantInstructorRoleId(
            Long roleId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsById(Long id);

    boolean existsByCourseIdAndInstructorId(
            Long courseId,
            Long instructorId
    );



    /*
     |--------------------------------------------------------------------------
     | Specific Record
     |--------------------------------------------------------------------------
     */

    Optional<CourseAssistantInstructor>
    findByCourseIdAndInstructorId(
            Long courseId,
            Long instructorId
    );



    /*
     |--------------------------------------------------------------------------
     | Instructor + Role
     |--------------------------------------------------------------------------
     */

    Page<CourseAssistantInstructor>
    findByInstructorAndAssistantInstructorRoleId(
            User instructor,
            Long roleId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Sorting
     |--------------------------------------------------------------------------
     */

    List<CourseAssistantInstructor>
    findByCourseIdOrderByIdAsc(
            Long courseId
    );

}