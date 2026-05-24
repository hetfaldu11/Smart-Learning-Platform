//package com.fm.smartlearningplatform.security.service;
//
//import com.fm.smartlearningplatform.exception.ResourceNotFoundException;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.repository.user.UserRepository;
//import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
//import com.fm.smartlearningplatform.security.principal.CustomUserPrincipal;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService
//        implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    private final UserRoleRepository userRoleRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(
//            String email
//    ) throws UsernameNotFoundException {
//
//        User user = userRepository
//                .findByEmailAndDeletedAtIsNull(email)
//                .orElseThrow(() ->
//                        new UsernameNotFoundException(
//                                "User not found"
//                        )
//                );
//
//        List<GrantedAuthority> authorities =
//                userRoleRepository
//                        .findByUserId(user.getId())
//                        .stream()
//                        .map(userRole ->
//                                new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getName())
//                        )
//                        .toList();
//
//        return new CustomUserPrincipal(
//                user.getId(),
//                user.getEmail(),
//                user.getPasswordHash(),
//                authorities
//        );
//    }
//}
//
