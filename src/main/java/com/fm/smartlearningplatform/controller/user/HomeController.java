package com.fm.smartlearningplatform.controller;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "Public";
    }

    @GetMapping("/secure")
    public String secure(
            Authentication authentication
    ) {

        return "Welcome : "
                + authentication.getDetails();
    }
}