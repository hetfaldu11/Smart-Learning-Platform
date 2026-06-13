package com.fm.smartlearningplatform.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseLevelRepository
        extends JpaRepository<CourseLevelRepository, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<CourseLevelRepository>
    findByIdAndDeletedAtIsNull(Long id);

    Optional<CourseLevelRepository>
    findByNameAndDeletedAtIsNull(String name);

    List<CourseLevelRepository>
    findByDeletedAtIsNull();

    Page<CourseLevelRepository>
    findByDeletedAtIsNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Deleted Records
     |--------------------------------------------------------------------------
     */

    List<CourseLevelRepository>
    findByDeletedAtIsNotNull();

    Page<CourseLevelRepository>
    findByDeletedAtIsNotNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<CourseLevelRepository>
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