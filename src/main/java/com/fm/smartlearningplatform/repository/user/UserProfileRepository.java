package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

}
