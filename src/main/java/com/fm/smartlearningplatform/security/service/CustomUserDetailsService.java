package com.fm.smartlearningplatform.security.service;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email)
    {
        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                        .orElseThrow(() -> new UsernameNotFoundException(email));
        Collection<? extends  GrantedAuthority>
                authorities =
                user.getUserRoles()
                        .stream()
                        .map(ur -> new SimpleGrantedAuthority(
                                ur.getRole().getName()))
                        .toList();
        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                null,
                authorities
        );
    }
}