package com.atwoz.admin.application.auth;

import com.atwoz.admin.application.auth.dto.AdminAccessTokenResponse;
import com.atwoz.admin.application.auth.dto.AdminLoginRequest;
import com.atwoz.admin.application.auth.dto.AdminProfileSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminTokenResponse;
import com.atwoz.admin.domain.admin.Admin;
import com.atwoz.admin.domain.admin.AdminRefreshToken;
import com.atwoz.admin.domain.admin.AdminRefreshTokenRepository;
import com.atwoz.admin.domain.admin.AdminRepository;
import com.atwoz.admin.domain.admin.service.AdminRefreshTokenProvider;
import com.atwoz.admin.exception.exceptions.AdminNotFoundException;
import com.atwoz.admin.exception.exceptions.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminAuthService {

    private final AdminRepository adminRepository;
    private final AdminAccessTokenProvider adminAccessTokenProvider;
    private final AdminRefreshTokenProvider adminRefreshTokenProvider;
    private final AdminRefreshTokenRepository adminRefreshTokenRepository;

    public AdminTokenResponse signUp(final AdminSignUpRequest adminSignUpRequest) {
        Admin admin = createAdmin(adminSignUpRequest);
        Admin savedAdmin = adminRepository.save(admin);
        AdminRefreshToken adminRefreshToken = createAdminRefreshToken(savedAdmin);
        adminRefreshTokenRepository.save(adminRefreshToken);
        String accessToken = adminAccessTokenProvider.createAccessToken(savedAdmin.getId());

        return new AdminTokenResponse(accessToken, adminRefreshToken.refreshToken());
    }

    private Admin createAdmin(final AdminSignUpRequest adminSignUpRequest) {
        AdminProfileSignUpRequest adminProfileSignUpRequest = adminSignUpRequest.adminProfileSignUpRequest();
        return Admin.createWith(
                adminSignUpRequest.email(),
                adminSignUpRequest.password(),
                adminSignUpRequest.confirmPassword(),
                adminProfileSignUpRequest.name(),
                adminProfileSignUpRequest.phoneNumber()
        );
    }

    private AdminRefreshToken createAdminRefreshToken(final Admin savedAdmin) {
        return AdminRefreshToken.createWith(
                adminRefreshTokenProvider,
                savedAdmin.getEmail(),
                savedAdmin.getId()
        );
    }

    public AdminTokenResponse login(final AdminLoginRequest adminLoginRequest) {
        Admin foundAdmin = findAdminByEmail(adminLoginRequest.email());
        foundAdmin.validatePassword(adminLoginRequest.password());
        AdminRefreshToken adminRefreshToken = createAdminRefreshToken(foundAdmin);
        adminRefreshTokenRepository.save(adminRefreshToken);
        String accessToken = adminAccessTokenProvider.createAccessToken(foundAdmin.getId());

        return new AdminTokenResponse(accessToken, adminRefreshToken.refreshToken());
    }

    private Admin findAdminByEmail(final String email) {
        return adminRepository.findAdminByEmail(email)
                .orElseThrow(AdminNotFoundException::new);
    }

    public AdminAccessTokenResponse reGenerateAccessToken(final String refreshToken) {
        AdminRefreshToken foundAdminRefreshToken = adminRefreshTokenRepository.findById(refreshToken)
                .orElseThrow(InvalidRefreshTokenException::new);
        Long memberId = foundAdminRefreshToken.memberId();
        String createdAccessToken = adminAccessTokenProvider.createAccessToken(memberId);

        return new AdminAccessTokenResponse(createdAccessToken);
    }

    public void logout(final String refreshToken) {
        adminRefreshTokenRepository.delete(refreshToken);
    }
}
