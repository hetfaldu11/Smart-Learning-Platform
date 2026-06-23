package com.fm.smartlearningplatform.common.config;

import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl
        implements AuditorAware<User> {

    @Override
    public Optional<User> getCurrentAuditor() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }

        UserPrincipal principal =
                (UserPrincipal) auth.getPrincipal();

        Optional<User> user = Optional.of(
                User.builder()
                        .id(principal.id())
                        .email(principal.email())
                        .build()
        );
        return user;
    }
}