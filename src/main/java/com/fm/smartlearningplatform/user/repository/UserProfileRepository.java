package com.fm.smartlearningplatform.user.repository;


import com.fm.smartlearningplatform.user.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}