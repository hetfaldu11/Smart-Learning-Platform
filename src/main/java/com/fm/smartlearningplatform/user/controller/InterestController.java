package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.interest.request.CreateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.request.UpdateInterestRequest;
import com.fm.smartlearningplatform.user.dto.interest.response.DeleteInterestResponse;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(interestService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterestResponse> getInterestById(@PathVariable Long id) {
        return ResponseEntity.ok(interestService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<InterestResponse>> getInterests(@RequestParam(value = "q", required = false) String keyword) {
        return ResponseEntity.ok(interestService.searchByKeyword(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterestResponse> updateInterestById(@PathVariable Long id, @Valid @RequestBody UpdateInterestRequest request) {
        return ResponseEntity.ok(interestService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteInterestResponse> deleteInterestById(@PathVariable Long id) {
        return ResponseEntity.ok().body(interestService.deleteById(id));
    }
}