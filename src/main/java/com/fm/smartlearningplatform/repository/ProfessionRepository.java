package com.fm.smartlearningplatform.repository;

import com.fm.smartlearningplatform.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession,Long> {
    Optional<Profession> findByName(String name);
}
