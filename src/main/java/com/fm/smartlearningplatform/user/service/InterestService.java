package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.response.InterestResponse;
import com.fm.smartlearningplatform.user.mapper.InterestMapper;
import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public InterestResponse createInterest(CreateInterestRequest request) {
        if (interestRepository.existsByNameAndDeletedAtIsNull(request.name()))
            throw new DuplicateResourceException("Interest already exists.");

        return interestMapper.toResponse(
                interestRepository.save(
                        interestMapper.toEntity(request)
                )
        );
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public InterestResponse updateInterest(Long id, UpdateInterestRequest request) {
        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found."));

        if (interestRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Interest already exists.");

        interestMapper.updateInterestFromRequest(request, interest);

        return interestMapper.toResponse(interestRepository.save(interest));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean existsByIdAndDeletedAtIsNull(Long id) {
        return interestRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public InterestResponse findByIdAndDeletedAtIsNull(Long id) {
        return interestMapper.toResponse(
                interestRepository.findByIdAndDeletedAtIsNull(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Interest not exists.")));
    }

    @Transactional(readOnly = true)
    public boolean existsByNameAndDeletedAtIsNull(String name) {
        return interestRepository.existsByNameAndDeletedAtIsNull(name);
    }

    @Transactional(readOnly = true)
    public InterestResponse findByNameAndDeletedAtIsNull(String name) {
        return interestMapper.toResponse(
                interestRepository.findByNameAndDeletedAtIsNull(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Interest not exists."))
        );
    }

    @Transactional(readOnly = true)
    public List<InterestResponse> findAllActive() {
        return interestRepository.findByDeletedAtIsNull()
                .stream()
                .map(interestMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id) {
        Interest interest = interestRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not exist."));

        interest.setDeletedAt(LocalDateTime.now());

        interestRepository.save(interest);
    }
}
