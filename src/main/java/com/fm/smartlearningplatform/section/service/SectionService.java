package com.fm.smartlearningplatform.section.service;

import com.fm.smartlearningplatform.course.model.Course;
import com.fm.smartlearningplatform.course.repository.CourseRepository;
import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.section.dto.section.request.CreateSectionRequest;
import com.fm.smartlearningplatform.section.dto.section.request.UpdateSectionRequest;
import com.fm.smartlearningplatform.section.dto.section.response.SectionResponse;
import com.fm.smartlearningplatform.section.mapper.SectionMapper;
import com.fm.smartlearningplatform.section.model.Section;
import com.fm.smartlearningplatform.section.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final SectionMapper sectionMapper;

    // ─── Create ───────────────────────────────────────────────

    @Transactional
    public SectionResponse create(CreateSectionRequest request) {
        Course course = getCourse(request.courseId());

        validatePositionNotExist(request.courseId(), request.position());

        Section section = sectionMapper.toEntity(request);
        section.setCourse(course);

        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    // ─── Find ─────────────────────────────────────────────────

    public SectionResponse findById(Long sectionId) {
        return sectionMapper.toResponse(getSection(sectionId));
    }

    public Page<SectionResponse> findAllByCourse(Long courseId, Pageable pageable) {
        return sectionRepository.findByCourseIdAndDeletedAtIsNullOrderByPositionAsc(courseId, pageable).map(sectionMapper::toResponse);
    }

    // ─── Update ───────────────────────────────────────────────

    @Transactional
    public SectionResponse update(Long sectionId, UpdateSectionRequest request) {
        Section section = getSection(sectionId);

        if (request.position() != null && !request.position().equals(section.getPosition())) {
            validatePositionNotExist(section.getCourse().getId(), section.getPosition());
        }
        sectionMapper.update(request, section);

        return sectionMapper.toResponse(sectionRepository.save(section));
    }

    // ─── Delete ───────────────────────────────────────────────

    @Transactional
    public void delete(Long sectionId) {
        Section section = getSection(sectionId);
        section.setDeletedAt(LocalDateTime.now());
        sectionRepository.save(section);
    }

    // ─── Exists ───────────────────────────────────────────────

    public boolean existsById(Long sectionId) {
        return sectionRepository.existsByIdAndDeletedAtIsNull(sectionId);
    }

    // ─── Helper ───────────────────────────────────────────────

    private Section getSection(Long sectionId) {
        return sectionRepository.findByIdAndDeletedAtIsNull(sectionId).orElseThrow(() -> new ResourceNotFoundException("Section not found."));
    }

    private Course getCourse(Long courseId) {

        return courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found."));
    }

    private void validatePositionNotExist(Long courseId, Integer position) {
        if (sectionRepository.existsByCourseIdAndPositionAndDeletedAtIsNull(courseId, position)) {
            throw new DuplicateResourceException("Section position already exists.");
        }
    }
}