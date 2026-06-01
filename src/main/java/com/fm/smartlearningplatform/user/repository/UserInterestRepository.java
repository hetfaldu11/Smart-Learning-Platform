package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

    // ─── Find ────────────────────────────────────────────────

    List<UserInterest> findByUserId(Long userId);

    List<UserInterest> findByInterestId(Long interestId);

    boolean existsByUserIdAndInterestId(Long userId, Long interestId);

    Optional<UserInterest> findByUserIdAndInterestId(Long userId, Long interestId);

    Optional<UserInterest> findByIdAndUserId(Long id, Long userId);

    @Query("""
            SELECT us.interest.id
            FROM UserInterest us
            WHERE us.user.id = :userId
            """)
    Set<Long> findInterestIdsByUserId(@Param("userId") Long userId);

    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all user_interests for a interest (used when interest is deleted)
    @Modifying
    @Query("DELETE FROM UserInterest us WHERE us.interest.id = :interestId")
    void deleteByInterestId(Long interestId);
}
