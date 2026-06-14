package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.AssistantInstructorRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssistantInstructorRoleRepository
        extends JpaRepository<AssistantInstructorRole, Long> {

    /*
     |--------------------------------------------------------------------------
     | Active Records
     |--------------------------------------------------------------------------
     */

    Optional<AssistantInstructorRole>
    findByIdAndDeletedAtIsNull(Long id);

    Optional<AssistantInstructorRole>
    findByNameAndDeletedAtIsNull(String name);

    List<AssistantInstructorRole>
    findByDeletedAtIsNull();

    Page<AssistantInstructorRole>
    findByDeletedAtIsNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Deleted Records
     |--------------------------------------------------------------------------
     */

    List<AssistantInstructorRole>
    findByDeletedAtIsNotNull();

    Page<AssistantInstructorRole>
    findByDeletedAtIsNotNull(Pageable pageable);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<AssistantInstructorRole>
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

    List<AssistantInstructorRole>
    findByDeletedAtIsNullOrderByNameAsc();

}