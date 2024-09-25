package com.atwoz.member.ui.auth;

import com.atwoz.member.application.auth.MemberAuthService;
import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.application.auth.dto.TestLoginRequest;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import com.atwoz.member.ui.auth.dto.TokenResponse;
import com.atwoz.member.ui.auth.support.OAuthAuthority;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/auth")
@RestController
public class MemberAuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final LoginRequest request,
                                               @OAuthAuthority final OAuthProviderRequest provider) {
        String token = memberAuthService.login(request, provider);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/test-login")
    public ResponseEntity<TokenResponse> testLogin(@RequestBody final TestLoginRequest request) {
        String token = memberAuthService.testLogin(request);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
