package com.fm.smartlearningplatform.lesson.repository;

import com.fm.smartlearningplatform.lesson.model.VideoResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoResourceRepository extends JpaRepository<VideoResource, Long> {
}
