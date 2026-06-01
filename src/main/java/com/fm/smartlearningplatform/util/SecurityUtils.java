package com.fm.smartlearningplatform.util;

import com.fm.smartlearningplatform.exceptionhandler.exception.UnauthorizedException;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserPrincipal getCurrentUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }

        if (!(auth.getPrincipal() instanceof UserPrincipal principal)) {
            throw new UnauthorizedException("User not authenticated");
        }

        return principal;
    }
}
