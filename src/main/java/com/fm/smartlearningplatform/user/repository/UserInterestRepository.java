package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

    // ─── Find ────────────────────────────────────────────────

    List<UserInterest> findByUserId(Long userId);

    List<UserInterest> findByInterestId(Long interestId);

    boolean existsByUserIdAndInterestId(Long userId, Long interestId);

    Optional<UserInterest> findByUserIdAndInterestId(Long userId, Long interestId);

    boolean existsByUserAndInterest(User user, Interest interest);

    Optional<UserInterest> findByUserAndInterest(User user, Interest interest);


    // ─── Delete ────────────────────────────────────────────────

    // Hard delete all user_interests for a interest (used when interest is deleted)
    @Modifying
    @Query("DELETE FROM UserInterest us WHERE us.interest.id = :interestId")
    void deleteByInterestId(@Param("interestId") Long interestId);
}
