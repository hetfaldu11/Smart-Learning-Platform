package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseLevelRepository
        extends JpaRepository<CourseLevel, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseLevel>
    findByIdAndDeletedAtIsNull(Long id);

    Optional<CourseLevel>
    findByNameAndDeletedAtIsNull(String name);

    List<CourseLevel>
    findByDeletedAtIsNull();

    Page<CourseLevel>
    findByDeletedAtIsNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Deleted Records
     |--------------------------------------------------------------------------
     */

    List<CourseLevel>
    findByDeletedAtIsNotNull();

    Page<CourseLevel>
    findByDeletedAtIsNotNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseLevel>
    findByDeletedAtIsNullAndNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    boolean existsByIdNotAndNameAndDeletedAtIsNull(
            Long id,
            String name
    );



    /*
     |--------------------------------------------------------------------------
     | Sorting
     |--------------------------------------------------------------------------
     */

    List<CourseLevel>
    findByDeletedAtIsNullOrderByNameAsc();

}