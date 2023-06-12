package com.swd392.funfundbe.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.swd392.funfundbe.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer "))
            return token.substring(7);
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token)) {
                FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
                String email = firebaseToken.getEmail();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        catch (FirebaseAuthException e) {
            log.warn("Fail to verify Firebase Token");
        }
        catch (Exception e) {
            log.warn("Fail to set user authentication");
        }
        filterChain.doFilter(request, response);
    }
}
