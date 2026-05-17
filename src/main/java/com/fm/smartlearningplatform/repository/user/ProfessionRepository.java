package com.fm.smartlearningplatform.repository.user;

import com.fm.smartlearningplatform.model.user.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    Optional<Profession> findByName(String name);
}
