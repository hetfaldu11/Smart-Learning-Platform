package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.userSkill.request.CreateUserSkillRequest;
import com.fm.smartlearningplatform.dto.user.userSkill.response.UserSkillResponse;
import com.fm.smartlearningplatform.service.user.UserSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-skills")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;

    @PostMapping
    public ResponseEntity<UserSkillResponse> createUserSkill(@Valid @RequestBody CreateUserSkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userSkillService.createUserSkill(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkillResponse>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userSkillService.findByUserId(userId));
    }

    @GetMapping("/skill/{skillId}")
    public ResponseEntity<List<UserSkillResponse>> findBySkillId(@PathVariable Long skillId) {
        return ResponseEntity.ok(userSkillService.findBySkillId(skillId));
    }

    @GetMapping("/user/{userId}/skill/{skillId}")
    public ResponseEntity<UserSkillResponse> findByUserIdAndSkillId(
            @PathVariable Long userId,
            @PathVariable Long skillId) {
        return ResponseEntity.ok(userSkillService.findByUserIdAndSkillId(userId, skillId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userSkillService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}