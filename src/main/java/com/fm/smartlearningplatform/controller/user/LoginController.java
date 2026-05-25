package com.fm.smartlearningplatform.controller.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @GetMapping("/secure")
    public String login(Authentication authentication){
//        if(authentication instanceof UsernamePasswordAuthenticationToken){
//            System.out.println(authentication);
//        }else if(authentication instanceof OAuth2AccessTokenAuthenticationToken){
//            System.out.println(authentication);
//        }
        return "Welcome " + authentication.getName();
    }
}