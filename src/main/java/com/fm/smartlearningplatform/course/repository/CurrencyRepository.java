package com.fm.smartlearningplatform.course.repository;

import com.fm.smartlearningplatform.course.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository
        extends JpaRepository<Currency, Long> {

    /*
     |--------------------------------------------------------------------------
     | Find Records
     |--------------------------------------------------------------------------
     */

    Optional<Currency>
    findById(Long id);

    Optional<Currency>
    findByName(String name);

    Optional<Currency>
    findByCode(String code);



    /*
     |--------------------------------------------------------------------------
     | Search
     |--------------------------------------------------------------------------
     */

    Page<Currency>
    findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Currency>
    findByCodeContainingIgnoreCase(
            String code,
            Pageable pageable
    );



    /*
     |--------------------------------------------------------------------------
     | Validation / Existence
     |--------------------------------------------------------------------------
     */

    boolean existsByName(String name);

    boolean existsByCode(String code);



    /*
     |--------------------------------------------------------------------------
     | Sorting
     |--------------------------------------------------------------------------
     */

    List<Currency>
    findAllByOrderByNameAsc();

}