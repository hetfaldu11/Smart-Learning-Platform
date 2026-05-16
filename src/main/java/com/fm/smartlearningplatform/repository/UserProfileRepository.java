package com.fm.smartlearningplatform.repository;


import com.fm.smartlearningplatform.model.User;
import com.fm.smartlearningplatform.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
