package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
