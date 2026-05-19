package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.platform.request.CreatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.request.UpdatePlatformRequest;
import com.fm.smartlearningplatform.dto.user.platform.response.PlatformResponse;
import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.service.user.PlatformService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(platformService.createPlatform(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponse> getPlatform(@PathVariable Long id) {
        return ResponseEntity.ok(platformService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponse>> getAllPlatforms() {
        return ResponseEntity.ok(platformService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponse> updatePlatform(@PathVariable Long id, @Valid @RequestBody UpdatePlatformRequest request) {
        return ResponseEntity.ok(platformService.updatePlatform(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlatform(@PathVariable Long id) {
        platformService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}