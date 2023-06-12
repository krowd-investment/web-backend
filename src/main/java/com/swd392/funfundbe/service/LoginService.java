package com.swd392.funfundbe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserDetailsServiceImpl userDetailsServiceImpl;
//    private final UserService userService;
//    @Value("${google.client-id}")
//    private String GOOGLE_CLIENT_ID;
//    private GoogleIdTokenVerifier verifier;
//
//    private final String TWILIO_ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
//    private final String TWILIO_AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
//    private final String TWILIO_SERVICE_SID = System.getenv("TWILIO_SERVICE_SID");
//
//    @PostConstruct
//    public void initVerifier() {
//        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
//                // Specify the CLIENT_ID of the app that accesses the backend:
//                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
//                // Or, if multiple clients access the backend:
//                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//                .build();
//    }
//
//    private LoginResponse loginAndGetToken(String username) {
//        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
//        //If email is valid, check account's status
//        if (userDetails.isAccountNonLocked()) {
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    userDetails,
//                    null,
//                    userDetails.getAuthorities()
//            );
//            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
//            String refreshToken = jwtTokenProvider.generateRefreshToken(username);
//            UserTbl user = ((UserDetailsImpl) userDetails).getUser();
//            return new LoginResponse(accessToken, refreshToken, user.getUserId(), user.getStatus().toString());
//        }
//        // Account cannot be accessed
//        throw new LockedException("Account is invalid now");
//    }
//
//    public LoginResponse authenticateGoogle(String idToken) {
//        GoogleIdToken googleIdToken;
//        try {
//            googleIdToken = verifier.verify(idToken);
//        }
//        catch (GeneralSecurityException | IOException e) {
//            throw new IllegalStateException(e.getMessage());
//        }
//
//        if (googleIdToken != null) {
//            GoogleIdToken.Payload payload = googleIdToken.getPayload();
//            String email = payload.getEmail();
//            try {
//                return loginAndGetToken(email);
//            }
//            catch (UsernameNotFoundException e) {
//                // Email is valid but account doesn't exist in database,
//                // should redirect to register page.
//                userService.createBasicUserViaGoogle(email);
//                return loginAndGetToken(email);
//            }
//            catch (LockedException e) {
//                throw new IllegalStateException(e.getMessage());
//            }
//        }
//        else {
//            // Invalid token
//            throw new BadCredentialsException("Error with Google authenticated");
//        }
//    }
//
//    @Async
//    public void generateOtpForPhone(String phone) {
//        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
//        Verification verification = Verification.creator(
//                TWILIO_SERVICE_SID,
//                phone,
//                "sms"
//        ).create();
//        log.info("Sending OTP status: " + verification.getStatus());
//        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());
//    }
//
//    public LoginResponse verifyOtpForPhone(LoginRequest request) {
//        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
//        VerificationCheck verificationCheck = VerificationCheck.creator(
//                TWILIO_SERVICE_SID
//        )
//                .setTo(request.getPhone())
//                .setCode(request.getOtp())
//                .create();
//        log.info(verificationCheck.getStatus());
//
//        if (verificationCheck.getStatus().equals("pending"))
//            throw new BadCredentialsException("OTP is not correct");
//        else if (verificationCheck.getStatus().equals("approved")) {
//            try {
//                return loginAndGetToken(request.getPhone());
//            }
//            catch (UsernameNotFoundException e) {
//                //Phone doesn't exist in database,
//                //should redirect to register page.
//                userService.createBasicUserViaPhone(request.getPhone());
//                return  loginAndGetToken(request.getPhone());
//            }
//            catch (LockedException e) {
//                throw new IllegalStateException(e.getMessage());
//            }
//
//        }
//        else {
//            throw new IllegalStateException("Something wrong with OTP verification");
//        }
//    }
}
