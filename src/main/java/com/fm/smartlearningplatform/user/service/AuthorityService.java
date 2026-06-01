package com.fm.smartlearningplatform.user.service;

import com.fm.smartlearningplatform.exceptionhandler.exception.DuplicateResourceException;
import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.user.dto.authority.request.CreateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.request.UpdateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.response.AuthorityResponse;
import com.fm.smartlearningplatform.user.dto.authority.response.DeleteAuthorityResponse;
import com.fm.smartlearningplatform.user.mapper.AuthorityMapper;
import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public AuthorityResponse create(CreateAuthorityRequest request) {
        validateAuthorityNotExist(request.name());
        return authorityMapper.toResponse(authorityRepository.save(authorityMapper.toEntity(request)));
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public AuthorityResponse update(Long id, UpdateAuthorityRequest request) {

        Authority authority = getAuthority(id);

        if (authorityRepository.existsByIdNotAndNameAndDeletedAtIsNull(id, request.name()))
            throw new DuplicateResourceException("Authority already exist.");

        authorityMapper.updateAuthorityFromRequest(request, authority);
        return authorityMapper.toResponse(authorityRepository.save(authority));
    }

    // ─── Find ────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public boolean foundById(Long id) {
        return authorityRepository.existsByIdAndDeletedAtIsNull(id);
    }

    @Transactional(readOnly = true)
    public AuthorityResponse findById(Long id) {
        return authorityMapper.toResponse(getAuthority(id));
    }

    @Transactional(readOnly = true)
    public List<AuthorityResponse> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        keyword = keyword.trim();
        return authorityRepository.findByDeletedAtIsNullAndNameContainingIgnoreCase(keyword)
                .stream()
                .map(authorityMapper::toResponse)
                .toList();
    }

    private List<AuthorityResponse> findAll() {
        return authorityRepository.findByDeletedAtIsNullOrderByNameAsc()
                .stream()
                .map(authorityMapper::toResponse)
                .toList();
    }

    // ─── Delete ────────────────────────────────────────────────

    @Transactional
    public DeleteAuthorityResponse deleteById(Long id) {
        Authority authority = getAuthority(id);

        authority.setDeletedAt(LocalDateTime.now());

        authorityRepository.save(authority);

        return new DeleteAuthorityResponse("Authority is deleted successfully.");
    }

    // ─── Helper ────────────────────────────────────────────────

    private void validateAuthorityNotExist(String name) {
        if (authorityRepository.existsByNameAndDeletedAtIsNull(name))
            throw new DuplicateResourceException("Authority already exist.");
    }

    private Authority getAuthority(Long id) {
        return authorityRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authority not found."));
    }
}
