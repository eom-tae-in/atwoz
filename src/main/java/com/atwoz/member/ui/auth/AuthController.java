package com.atwoz.member.ui.auth;

import com.atwoz.member.application.auth.AuthService;
import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import com.atwoz.member.ui.auth.dto.TokenResponse;
import com.atwoz.member.ui.auth.support.auth.OAuthAuthority;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final LoginRequest request,
                                               @OAuthAuthority final OAuthProviderRequest provider) {
        String token = authService.login(request, provider);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
