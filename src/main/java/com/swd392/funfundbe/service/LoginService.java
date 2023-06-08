package com.swd392.funfundbe.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.swd392.funfundbe.model.Response.LoginResponse;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.security.UserDetailsImpl;
import com.swd392.funfundbe.security.UserDetailsServiceImpl;
import com.swd392.funfundbe.security.jwt.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final UserService userService;
    @Value("${google.client-id}")
    private String GOOGLE_CLIENT_ID;
    private GoogleIdTokenVerifier verifier;

    @PostConstruct
    public void initVerifier() {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
    }

    public LoginResponse authenticateGoogle(String idToken) {
        GoogleIdToken googleIdToken;
        try {
            googleIdToken = verifier.verify(idToken);
        }
        catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();

            try {
                return loginEmailAndGetToken(email);
            }
            catch (UsernameNotFoundException e) {
                // Email is valid but account doesn't exist in database,
                // should redirect to register page.
                userService.createBasicUserViaGoogle(email);
                return loginEmailAndGetToken(email);
            }
            catch (LockedException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
        else {
            // Invalid token
            throw new BadCredentialsException("Error with Google authenticated");
        }
    }

    private LoginResponse loginEmailAndGetToken(String email) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
        //If email is valid, check account's status
        if (userDetails.isAccountNonLocked()) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(email);
            UserTbl user = ((UserDetailsImpl) userDetails).getUser();
            return new LoginResponse(accessToken, refreshToken, user.getUserId(), user.getStatus().toString());
        }
        // Account cannot be accessed
        throw new LockedException("Account is invalid now");
    }
}
