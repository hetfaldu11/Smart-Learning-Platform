package com.fm.smartlearningplatform.section.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.section.dto.sectionProgress.request.CreateSectionProgressRequest;
import com.fm.smartlearningplatform.section.dto.sectionProgress.request.UpdateSectionProgressRequest;
import com.fm.smartlearningplatform.section.dto.sectionProgress.response.SectionProgressResponse;
import com.fm.smartlearningplatform.section.mapper.SectionProgressMapper;
import com.fm.smartlearningplatform.section.model.Section;
import com.fm.smartlearningplatform.section.model.SectionProgress;
import com.fm.smartlearningplatform.section.repository.SectionProgressRepository;
import com.fm.smartlearningplatform.section.repository.SectionRepository;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SectionProgressService {

    private final SectionProgressRepository sectionProgressRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final SectionProgressMapper sectionProgressMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public SectionProgressResponse create(CreateSectionProgressRequest request) {

        User user = getUser(request.userId());

        Section section = getSection(request.sectionId());

        validateSectionProgressNotExists(request.userId(), request.sectionId());

        SectionProgress sectionProgress = sectionProgressMapper.toEntity(request);

        sectionProgress.setUser(user);
        sectionProgress.setSection(section);

        return sectionProgressMapper.toResponse(sectionProgressRepository.save(sectionProgress));
    }

    // ─── Find ─────────────────────────────────────────────────

    public SectionProgressResponse findByUserAndSection(Long userId, Long sectionId) {
        return sectionProgressMapper.toResponse(getSectionProgress(userId, sectionId));
    }

    public Page<SectionProgressResponse> findAllByUser(Long userId, Pageable pageable) {
        return sectionProgressRepository.findByUserId(userId, pageable).map(sectionProgressMapper::toResponse);
    }

    public Page<SectionProgressResponse> findAllBySection(Long sectionId, Pageable pageable) {
        return sectionProgressRepository.findBySectionId(sectionId, pageable).map(sectionProgressMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public SectionProgressResponse update(Long userId, Long sectionId, UpdateSectionProgressRequest request) {

        SectionProgress progress = getSectionProgress(userId, sectionId);

        progress.setCompletedLessons(request.completedLessons());

        if (request.completedLessons() >= progress.getTotalLessons()) {
            progress.setCompleted(true);
            progress.setCompletedAt(LocalDateTime.now());

        } else {
            progress.setCompleted(false);
            progress.setCompletedAt(null);
        }

        return sectionProgressMapper.toResponse(sectionProgressRepository.save(progress));
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean exists(Long userId, Long sectionId) {

        return sectionProgressRepository.existsByUserIdAndSectionId(userId, sectionId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private SectionProgress getSectionProgress(Long userId, Long sectionId) {

        return sectionProgressRepository.findByUserIdAndSectionId(userId, sectionId).orElseThrow(() -> new ResourceNotFoundException("Section progress not found."));
    }

    private User getUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    private Section getSection(Long sectionId) {

        return sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Section not found."));
    }

    private void validateSectionProgressNotExists(Long userId, Long sectionId) {

        if (sectionProgressRepository.existsByUserIdAndSectionId(userId, sectionId)) {

            throw new DuplicateResourceException("Section progress already exists.");
        }
    }
}