package com.swd392.funfundbe.service;

import com.swd392.funfundbe.model.Response.AuthenticateResponse;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticateService {

    private static UserTbl getCurrentUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetailsImpl) principal).getUser();
            }
        }
        throw new UsernameNotFoundException("Current User not found in the security context");
    }

    public AuthenticateResponse authenticateUser() {
        UserTbl user = getCurrentUserFromSecurityContext();
        return new AuthenticateResponse(
                user.getUserId(),
                user.getStatus().toString(),
                user.isEnabled()
        );
    }

    public static boolean checkCurrentUser() {
        UserTbl user = getCurrentUserFromSecurityContext();
        return user.isEnabled();
    }
}
