package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.authority.request.CreateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.request.UpdateAuthorityRequest;
import com.fm.smartlearningplatform.user.dto.authority.response.AuthorityResponse;
import com.fm.smartlearningplatform.user.dto.authority.response.DeleteAuthorityResponse;
import com.fm.smartlearningplatform.user.service.AuthorityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authorities")
@RequiredArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @PostMapping
    public ResponseEntity<AuthorityResponse> createAuthority(@Valid @RequestBody CreateAuthorityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorityService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorityResponse> getAuthorityById(@PathVariable Long id) {
        return ResponseEntity.ok(authorityService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorityResponse>> getAuthorities(@RequestParam(value = "q", required = false) String keyword, @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(authorityService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorityResponse> updateAuthorityById(@PathVariable Long id, @Valid @RequestBody UpdateAuthorityRequest request) {
        return ResponseEntity.ok(authorityService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteAuthorityResponse> deleteAuthorityById(@PathVariable Long id) {
        return ResponseEntity.ok().body(authorityService.deleteById(id));
    }
}