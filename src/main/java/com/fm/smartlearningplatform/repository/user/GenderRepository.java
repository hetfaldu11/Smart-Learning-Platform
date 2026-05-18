package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}
