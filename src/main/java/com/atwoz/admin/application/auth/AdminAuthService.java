package com.atwoz.admin.application.auth;

import com.atwoz.admin.application.auth.dto.AdminAccessTokenResponse;
import com.atwoz.admin.application.auth.dto.AdminLoginRequest;
import com.atwoz.admin.application.auth.dto.AdminProfileSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminTokenResponse;
import com.atwoz.admin.domain.admin.Admin;
import com.atwoz.admin.domain.admin.AdminRepository;
import com.atwoz.admin.domain.admin.AdminTokenProvider;
import com.atwoz.admin.exception.exceptions.AdminNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminAuthService {

    private static final String ID = "id";

    private final AdminRepository adminRepository;
    private final AdminTokenProvider adminTokenProvider;

    public AdminTokenResponse signUp(final AdminSignUpRequest adminSignUpRequest) {
        AdminProfileSignUpRequest adminProfileSignUpRequest = adminSignUpRequest.adminProfileSignUpRequest();
        Admin admin = Admin.createWith(
                adminSignUpRequest.email(),
                adminSignUpRequest.password(),
                adminSignUpRequest.confirmPassword(),
                adminProfileSignUpRequest.name(),
                adminProfileSignUpRequest.phoneNumber()
        );
        Admin savedAdmin = adminRepository.save(admin);

        return createAdminTokenResponse(savedAdmin.getId());
    }

    private AdminTokenResponse createAdminTokenResponse(final Long id) {
        return new AdminTokenResponse(
                adminTokenProvider.createAccessToken(id),
                adminTokenProvider.createRefreshToken(id)
        );
    }

    public AdminTokenResponse login(final AdminLoginRequest adminLoginRequest) {
        Admin foundAdmin = findAdminByEmail(adminLoginRequest.email());
        foundAdmin.validatePassword(adminLoginRequest.password());

        return createAdminTokenResponse(foundAdmin.getId());
    }

    public AdminAccessTokenResponse reGenerateAccessToken(final String refreshToken) {
        Admin foundAdmin = findAdminById(adminTokenProvider.extract(refreshToken, ID, Long.class));

        return new AdminAccessTokenResponse(adminTokenProvider.createAccessToken(foundAdmin.getId()));
    }

    private Admin findAdminByEmail(final String email) {
        return adminRepository.findAdminByEmail(email)
                .orElseThrow(AdminNotFoundException::new);
    }

    private Admin findAdminById(final Long id) {
        return adminRepository.findAdminById(id)
                .orElseThrow(AdminNotFoundException::new);
    }
}
