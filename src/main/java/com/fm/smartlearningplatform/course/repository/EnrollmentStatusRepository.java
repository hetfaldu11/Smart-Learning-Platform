package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentStatusRepository
        extends JpaRepository<EnrollmentStatus, Long> {

    /*
     |--------------------------------------------------------------------------
     | Find Records
     |--------------------------------------------------------------------------
     */

    Optional<EnrollmentStatus>
    findById(Long id);

    Optional<EnrollmentStatus>
    findByName(String name);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<EnrollmentStatus>
    findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<EnrollmentStatus>
    findByDescriptionContainingIgnoreCase(
            String description,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsById(Long id);

    boolean existsByName(String name);



    /*
     |--------------------------------------------------------------------------
     | Sorting
     |--------------------------------------------------------------------------
     */

    List<EnrollmentStatus>
    findAllByOrderByNameAsc();

}