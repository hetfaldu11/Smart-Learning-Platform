//package com.fm.smartlearningplatform.controller.user;
//
//import com.fm.smartlearningplatform.dto.user.login.CreateUserRequest;
//import com.fm.smartlearningplatform.dto.request.LoginRequest;
//import com.fm.smartlearningplatform.dto.user.login.AuthResponse;
//import com.fm.smartlearningplatform.model.user.Role;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.model.user.UserRole;
//import com.fm.smartlearningplatform.repository.user.RoleRepository;
//import com.fm.smartlearningplatform.repository.user.UserRepository;
//import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import com.fm.smartlearningplatform.security.jwt.JwtService;
//import com.fm.smartlearningplatform.security.principal.CustomUserPrincipal;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//import org.springframework.security.core.Authentication;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UserRepository userRepository;
//
//    private final RoleRepository roleRepository;
//
//    private final UserRoleRepository userRoleRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final AuthenticationManager authenticationManager;
//
//    private final JwtService jwtService;
//
//    @PostMapping("/register")
//    public String register(
//            @Valid
//            @RequestBody
//            CreateUserRequest request
//    ) {
//
//        boolean exists =
//                userRepository
//                        .existsByEmailAndDeletedAtIsNull(
//                                request.getEmail()
//                        );
//
//        if (exists) {
//
//            throw new RuntimeException(
//                    "Email already exists"
//            );
//        }
//
//        User user = User.builder()
//
//                .email(
//                        request.getEmail()
//                )
//
//                .passwordHash(
//                        passwordEncoder.encode(
//                                request.getPassword()
//                        )
//                )
//
//                .build();
//
//        User savedUser =
//                userRepository.save(user);
//
//        Role role =
//                roleRepository
//                        .findByName("USER")
//                        .orElseThrow(() ->
//                                new RuntimeException(
//                                        "Role not found"
//                                )
//                        );
//
//        UserRole userRole =
//                UserRole.builder()
//
//                        .user(savedUser)
//
//                        .role(role)
//
//                        .build();
//
//        userRoleRepository.save(userRole);
//
//        return "User registered successfully";
//    }
//
//    @PostMapping("/login")
//    public AuthResponse login(
//            @Valid
//            @RequestBody
//            LoginRequest request
//    ) {
//
//        Authentication authentication =
//                authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                request.getEmail(),
//                                request.getPassword()
//                        )
//                );
//
//        CustomUserPrincipal principal =
//                (CustomUserPrincipal)
//                        authentication.getPrincipal();
//
//        String token =
//                jwtService.generateToken(
//                        principal.getEmail()
//                );
//
//        return new AuthResponse(token);
//    }
//}
