package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.response.InterestResponse;
import com.fm.smartlearningplatform.user.service.InterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<InterestResponse> createInterest(@Valid @RequestBody CreateInterestRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interestService.createInterest(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestResponse> getInterest(@PathVariable Long id) {
        return ResponseEntity.ok(interestService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<InterestResponse>> getAllInterests() {
        return ResponseEntity.ok(interestService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestResponse> updateInterest(@PathVariable Long id, @Valid @RequestBody UpdateInterestRequest request) {
        return ResponseEntity.ok(interestService.updateInterest(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterest(@PathVariable Long id) {
        interestService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}