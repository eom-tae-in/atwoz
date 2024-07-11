package com.atwoz.admin.ui.auth;

import com.atwoz.admin.application.auth.AdminAuthService;
import com.atwoz.admin.application.auth.dto.AdminAccessTokenResponse;
import com.atwoz.admin.application.auth.dto.AdminLoginRequest;
import com.atwoz.admin.application.auth.dto.AdminSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminTokenResponse;
import com.atwoz.admin.ui.auth.support.AdminRefreshToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admins/auth")
public class AdminAuthController {

    private static final String COOKIE_NAME = "refreshToken";
    private static final String ANY_WAY = "/";

    @Value("${cookie.max-age}")
    private int maxAge;

    private final AdminAuthService adminAuthService;

    @PostMapping("/sign-up")
    public ResponseEntity<AdminAccessTokenResponse> singUp(
            @RequestBody @Valid final AdminSignUpRequest adminSignUpRequest) {
        AdminTokenResponse adminTokenResponse = adminAuthService.signUp(adminSignUpRequest);

        return ResponseEntity.ok()
                .headers(createCookieHeaders(adminTokenResponse.refreshToken()))
                .body(new AdminAccessTokenResponse(adminTokenResponse.accessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AdminAccessTokenResponse> login(
            @RequestBody @Valid final AdminLoginRequest adminLoginRequest) {
        AdminTokenResponse adminTokenResponse = adminAuthService.login(adminLoginRequest);

        return ResponseEntity.ok()
                .headers(createCookieHeaders(adminTokenResponse.refreshToken()))
                .body(new AdminAccessTokenResponse(adminTokenResponse.accessToken()));
    }

    @PostMapping("/access-token-regeneration")
    public ResponseEntity<AdminAccessTokenResponse> reGenerateAccessToken(
            @AdminRefreshToken final String refreshToken) {
        return ResponseEntity.ok(adminAuthService.reGenerateAccessToken(refreshToken));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@AdminRefreshToken final String refreshToken) {
        adminAuthService.logout(refreshToken);

        return ResponseEntity.ok()
                .build();
    }


    private HttpHeaders createCookieHeaders(final String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path(ANY_WAY)
                .maxAge(maxAge)
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return httpHeaders;
    }
}
