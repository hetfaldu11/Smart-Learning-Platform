package com.fm.smartlearningplatform.common.repository;

import com.fm.smartlearningplatform.common.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
