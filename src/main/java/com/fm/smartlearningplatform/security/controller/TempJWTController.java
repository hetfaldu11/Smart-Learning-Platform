package com.fm.smartlearningplatform.security.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempJWTController {

    @GetMapping("/secure")
    public ResponseEntity<String> generateToken(){
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body("Jwt token is created.");
    }
}
