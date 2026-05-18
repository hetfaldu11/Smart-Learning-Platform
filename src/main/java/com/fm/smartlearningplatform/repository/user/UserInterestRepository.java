package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserInterestRepository extends JpaRepository<UserInterest,Long> {

//    List<UserInterest> findByUserId(Long userId);
//
//    List<UserInterest> findByInterestId(Long interestId);
//
//    Optional<UserInterest> findByUserIdAndInterestId(Long userId, Long interestId);
//
//    Optional<UserInterest> findByUserAndInterest(User user, Interest interest);
//
//    boolean existsByUserIdAndInterestId(Long userId, Long interestId);
//
//    // Hard delete all user_interests for a user (used when user is deleted)
//    @Modifying
//    @Query("DELETE FROM UserInterest us WHERE us.user.id = :userId")
//    void deleteByUserId(@Param("userId") Long userId);
//
//    // Hard delete all user_interests for a interest (used when interest is deleted)
//    @Modifying
//    @Query("DELETE FROM UserInterest us WHERE us.interest.id = :interestId")
//    void deleteByInterestId(@Param("interestId") Long interestId);
}
