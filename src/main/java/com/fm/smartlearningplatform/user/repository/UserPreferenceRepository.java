package com.fm.smartlearningplatform.user.repository;

import com.fm.smartlearningplatform.user.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

}
