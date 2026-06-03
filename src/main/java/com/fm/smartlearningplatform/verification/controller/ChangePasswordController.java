//package com.fm.smartlearningplatform.verification.controller;
//
//import com.fm.smartlearningplatform.security.principal.UserPrincipal;
//import com.fm.smartlearningplatform.verification.dto.request.ChangePasswordRequest;
//import com.fm.smartlearningplatform.verification.service.ChangePasswordService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1")
//@RequiredArgsConstructor
//public class ChangePasswordController {
//
//    private final ChangePasswordService changePasswordService;
//
//    @PatchMapping("/user/change-password")
//    public ResponseEntity<Void> changePassword(
//            @AuthenticationPrincipal UserPrincipal principal,
//            @RequestBody @Valid ChangePasswordRequest request
//    ) {
//
//        changePasswordService.changePassword(principal.id(), request);
//        return ResponseEntity.noContent().build();
//    }
//}
