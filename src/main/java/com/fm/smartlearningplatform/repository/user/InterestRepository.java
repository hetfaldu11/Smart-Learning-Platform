package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
//    List<Interest> findByDeletedAtIsNull();
//
//    Optional<Interest> findByIdAndDeletedAtIsNull(Long id);
//
//    boolean existsByNameAndDeletedAtIsNull(String name);
}
