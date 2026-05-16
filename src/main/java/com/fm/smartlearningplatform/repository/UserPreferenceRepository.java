package com.fm.smartlearningplatform.repository;

import com.fm.smartlearningplatform.model.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {
}
