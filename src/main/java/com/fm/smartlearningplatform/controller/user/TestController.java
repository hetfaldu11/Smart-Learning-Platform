package com.fm.smartlearningplatform.controller.user;


import com.fm.smartlearningplatform.security.principal.CustomUserDetails;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/me")
    public String me(Authentication authentication)
    {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        return "User Id = " + user.getId() + " Email = " + user.getEmail();
    }
}
