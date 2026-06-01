package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.user.dto.platform.response.DeletePlatformResponse;
import com.fm.smartlearningplatform.user.dto.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.user.service.PlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platforms")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;

    @PostMapping
    public ResponseEntity<PlatformResponse> createPlatform(@Valid @RequestBody CreatePlatformRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platformService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponse> getPlatformById(@PathVariable Long id) {
        return ResponseEntity.ok(platformService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponse>> getPlatforms(@RequestParam(value = "q", required = false) String keyword) {
        return ResponseEntity.ok(platformService.searchByKeyword(keyword));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponse> updatePlatformById(@PathVariable Long id, @Valid @RequestBody UpdatePlatformRequest request) {
        return ResponseEntity.ok(platformService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletePlatformResponse> deletePlatformById(@PathVariable Long id) {
        return ResponseEntity.ok().body(platformService.deleteById(id));
    }
}