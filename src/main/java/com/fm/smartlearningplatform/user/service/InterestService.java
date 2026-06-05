package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.response.DeleteInterestResponse;
import com.fm.smartlearningplatform.user.dto.interest.response.InterestResponse;
import com.fm.smartlearningplatform.user.dto.interest.response.InterestResponse;
import com.fm.smartlearningplatform.user.mapper.InterestMapper;
import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.model.Interest;
import com.fm.smartlearningplatform.user.repository.InterestRepository;
import com.fm.smartlearningplatform.user.repository.UserInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;
    private final InterestMapper interestMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public InterestResponse create(CreateInterestRequest request) {
        validateInterestNotExist(request.name());
        return interestMapper.toResponse(interestRepository.save(interestMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public InterestResponse update(Long id, UpdateInterestRequest request) {

        Interest interest = getInterest(id);

        if (interestRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Interest already exist.");

        interestMapper.updateInterestFromRequest(request, interest);
        return interestMapper.toResponse(interestRepository.save(interest));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return interestRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public InterestResponse findById(Long id) {
        return interestMapper.toResponse(getInterest(id));
    }

    @Transactional(readOnly = true)
    public Page<InterestResponse> searchByKeyword(String keyword, Pageable pageable)
    {
        Page<Interest> interests;
        if (keyword == null || keyword.isBlank()) {
            interests = interestRepository.findByDeletedAtIsNull(pageable);
        } else {
            keyword = keyword.trim();
            interests = interestRepository .findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword, pageable);
        }
        return interests.map(interestMapper::toResponse);
    }
    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteInterestResponse deleteById(Long id) {
        Interest interest = getInterest(id);
        userInterestRepository.deleteByInterestId(id);

        interest.setDeletedAt(LocalDateTime.now());

        interestRepository.save(interest);

        return new DeleteInterestResponse("Interest is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateInterestNotExist(String name) {
        if (interestRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Interest already exist.");
    }

    private Interest getInterest(Long id) {
        return interestRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interest not found."));
    }
}
