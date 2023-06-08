package com.swd392.funfundbe.security.jwt;

import com.swd392.funfundbe.security.UserDetailsServiceImpl;
import com.swd392.funfundbe.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.access-secret-key}")
    private String accessSecretKey;
    @Value("${jwt.refresh-secret-key}")
    private String refreshSecretKey;
    @Value("${jwt.access-expire-time-in-minutes}")
    private Long accessExpireTimeInMinutes;
    @Value("${jwt.refresh-expire-time-in-minutes}")
    private Long refreshExpireTimeInMinutes;
    private JwtBuilder accessJwtBuidler;
    private JwtParser accessJwtParser;
    private JwtBuilder refreshJwtBuidler;
    private JwtParser refreshJwtParser;

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserService userService;

    @PostConstruct
    public void postConstruct() {
        SecretKey accessKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_16));
        SecretKey refreshKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_16));
        accessJwtBuidler = Jwts.builder().signWith(accessKey);
        accessJwtParser = Jwts.parserBuilder().setSigningKey(accessKey).build();
        refreshJwtBuidler = Jwts.builder().signWith(refreshKey);
        refreshJwtParser = Jwts.parserBuilder().setSigningKey(refreshKey).build();
    }

    public String getAccessToken(String email, String role) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + accessExpireTimeInMinutes * 60 * 1000);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return accessJwtBuidler
                .setSubject(email)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        String email = authentication.getName();
        String role = userService.getUserByEmail(email).getRole().getRoleId();
        return getAccessToken(email, role);
    }

    public String generateAccessToken(String refreshToken) {
        Claims refreshClaims = refreshJwtParser.parseClaimsJws(refreshToken).getBody();
        String email = refreshClaims.getSubject();
        String role = userService.getUserByEmail(email).getRole().getRoleId();
        return getAccessToken(email, role);
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + refreshExpireTimeInMinutes * 60 * 1000);
        return refreshJwtBuidler
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            accessJwtParser.parseClaimsJws(accessToken);
            return true;
        }
        catch (MalformedJwtException e) {
            log.warn("Invalid JWT token");
        }
        catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
        }
        catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token");
        }
        catch (SignatureException e) {
            log.warn("JWT signature validation fails");
        }
        catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty.");
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            refreshJwtParser.parseClaimsJws(refreshToken);
            return true;
        }
        catch (MalformedJwtException e) {
            log.warn("Invalid JWT token");
        }
        catch (ExpiredJwtException e) {
            log.warn("Expired JWT token");
        }
        catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token");
        }
        catch (SignatureException e) {
            log.warn("JWT signature validation fails");
        }
        catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty.");
        }
        return false;
    }



}
