package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.dto.user.skill.response.SkillResponse;
import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.service.user.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody CreateSkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.createSkill(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkill(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.findByIdAndDeletedAtIsNull(id));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.findAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkill(@PathVariable Long id, @Valid @RequestBody UpdateSkillRequest request) {
        return ResponseEntity.ok(skillService.updateSkill(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}