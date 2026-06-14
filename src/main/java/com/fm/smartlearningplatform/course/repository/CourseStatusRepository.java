package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStatusRepository
        extends JpaRepository<CourseStatus, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseStatus>
    findByIdAndDeletedAtIsNull(Long id);

    Optional<CourseStatus>
    findByNameAndDeletedAtIsNull(String name);

    List<CourseStatus>
    findByDeletedAtIsNull();

    Page<CourseStatus>
    findByDeletedAtIsNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Deleted Records
     |--------------------------------------------------------------------------
     */

    List<CourseStatus>
    findByDeletedAtIsNotNull();

    Page<CourseStatus>
    findByDeletedAtIsNotNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseStatus>
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

    List<CourseStatus>
    findByDeletedAtIsNullOrderByNameAsc();

}