package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.CourseMessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseMessageTypeRepository
        extends JpaRepository<CourseMessageType, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseMessageType>
    findByIdAndDeletedAtIsNull(Long id);

    Optional<CourseMessageType>
    findByNameAndDeletedAtIsNull(String name);

    List<CourseMessageType>
    findByDeletedAtIsNull();

    Page<CourseMessageType>
    findByDeletedAtIsNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Deleted Records
     |--------------------------------------------------------------------------
     */

    List<CourseMessageType>
    findByDeletedAtIsNotNull();

    Page<CourseMessageType>
    findByDeletedAtIsNotNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseMessageType>
    findByNameContainingIgnoreCaseAndDeletedAtIsNull(
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

}