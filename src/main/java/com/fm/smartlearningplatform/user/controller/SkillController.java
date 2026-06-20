package com.fm.smartlearningplatform.user.controller;

import com.fm.smartlearningplatform.user.dto.skill.request.CreateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.request.UpdateSkillRequest;
import com.fm.smartlearningplatform.user.dto.skill.response.DeleteSkillResponse;
import com.fm.smartlearningplatform.user.dto.skill.response.SkillResponse;
import com.fm.smartlearningplatform.user.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody CreateSkillRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(skillService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SkillResponse>> getSkills(@RequestParam(value = "q", required = false) String keyword,
                                                         @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(skillService.searchByKeyword(keyword, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> updateSkillById(@PathVariable Long id, @Valid @RequestBody UpdateSkillRequest request) {
        return ResponseEntity.ok(skillService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSkillResponse> deleteSkillById(@PathVariable Long id) {
        return ResponseEntity.ok().body(skillService.deleteById(id));
    }
}