package com.fm.smartlearningplatform.security.authenticationprovider;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    public final PasswordEncoder passwordEncoder;
    public final UserRepository userRepository;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new BadCredentialsException("Email not found."));

        Long id = user.getId();

        List<GrantedAuthority> authorities = userRepository.findAuthoritiesByUserId(id)
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toUnmodifiableList());


        if(passwordEncoder.matches(pwd,user.getPasswordHash())){
            return new UsernamePasswordAuthenticationToken(new UserPrincipal(user.getId(),email),null,
                    authorities);
        }

        throw new BadCredentialsException("Email and password not found.");
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
