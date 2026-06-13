package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseLanguage;
import com.fm.smartlearningplatform.user.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseLanguageRepository
        extends JpaRepository<CourseLanguage, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseLanguage>
    findByIdAndCourseDeletedAtIsNull(Long id);



    /*
     |--------------------------------------------------------------------------
     | Course Languages
     |--------------------------------------------------------------------------
     */

    List<CourseLanguage>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId
    );

    Page<CourseLanguage>
    findByCourseIdAndCourseDeletedAtIsNull(
            Long courseId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Language Courses
     |--------------------------------------------------------------------------
     */

    List<CourseLanguage>
    findByLanguageIdAndCourseDeletedAtIsNull(
            Long languageId
    );

    Page<CourseLanguage>
    findByLanguageIdAndCourseDeletedAtIsNull(
            Long languageId,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Primary Language
     |--------------------------------------------------------------------------
     */

    Optional<CourseLanguage>
    findByCourseIdAndIsPrimaryTrueAndCourseDeletedAtIsNull(
            Long courseId
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByCourseIdAndLanguageIdAndCourseDeletedAtIsNull(
            Long courseId,
            Long languageId
    );



    /*
     |--------------------------------------------------------------------------
     | Specific Record
     |--------------------------------------------------------------------------
     */

    Optional<CourseLanguage>
    findByCourseIdAndLanguageIdAndCourseDeletedAtIsNull(
            Long courseId,
            Long languageId
    );



    /*
     |--------------------------------------------------------------------------
     | Search By Language Entity
     |--------------------------------------------------------------------------
     */

    List<CourseLanguage>
    findByLanguageAndCourseDeletedAtIsNull(
            Language language
    );

}