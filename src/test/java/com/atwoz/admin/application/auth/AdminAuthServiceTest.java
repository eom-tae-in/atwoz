package com.atwoz.admin.application.auth;

import com.atwoz.admin.application.auth.dto.AdminAccessTokenResponse;
import com.atwoz.admin.application.auth.dto.AdminLoginRequest;
import com.atwoz.admin.application.auth.dto.AdminSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminTokenResponse;
import com.atwoz.admin.domain.admin.AdminRepository;
import com.atwoz.admin.domain.admin.AdminTokenProvider;
import com.atwoz.admin.exception.exceptions.AdminNotFoundException;
import com.atwoz.admin.exception.exceptions.InvalidPasswordException;
import com.atwoz.admin.infrastructure.admin.AdminFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.atwoz.admin.fixture.AdminFixture.관리자_생성;
import static com.atwoz.admin.fixture.AdminRequestFixture.관리자_로그인_요청;
import static com.atwoz.admin.fixture.AdminRequestFixture.관리자_회원_가입_요청;
import static com.atwoz.admin.fixture.AdminTokenResponseFixture.관리자_액세스_토큰_생성_응답;
import static com.atwoz.admin.fixture.AdminTokenResponseFixture.관리자_토큰_생성_응답;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class AdminAuthServiceTest {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String ID = "id";

    @Mock
    private AdminTokenProvider adminTokenProvider;
    private AdminAuthService adminAuthService;
    private AdminRepository adminRepository;

    @BeforeEach
    void setup() {
        adminRepository = new AdminFakeRepository();
        adminAuthService = new AdminAuthService(adminRepository, adminTokenProvider);
    }

    @Test
    void 회원_가입을_진행하면_토큰을_반환한다() {
        // given
        AdminSignUpRequest adminSignUpRequest = 관리자_회원_가입_요청();
        AdminTokenResponse adminTokenResponse = 관리자_토큰_생성_응답();
        when(adminTokenProvider.createAccessToken(any())).thenReturn(adminTokenResponse.accessToken());
        when(adminTokenProvider.createRefreshToken(any())).thenReturn(adminTokenResponse.refreshToken());

        // when
        AdminTokenResponse response = adminAuthService.signUp(adminSignUpRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.accessToken()).isEqualTo(adminTokenResponse.accessToken());
            softly.assertThat(response.refreshToken()).isEqualTo(adminTokenResponse.refreshToken());
        });
    }

    @Nested
    class 로그인 {

        @Test
        void 로그인_입력이_올바르면_토큰을_반환한다() {
            // given
            adminRepository.save(관리자_생성());
            AdminLoginRequest adminLoginRequest = 관리자_로그인_요청();
            AdminTokenResponse adminTokenResponse = 관리자_토큰_생성_응답();
            when(adminTokenProvider.createAccessToken(any())).thenReturn(adminTokenResponse.accessToken());
            when(adminTokenProvider.createRefreshToken(any())).thenReturn(adminTokenResponse.refreshToken());

            // when
            AdminTokenResponse response = adminAuthService.login(adminLoginRequest);

            // then
            assertSoftly(softly -> {
                softly.assertThat(response.accessToken()).isEqualTo(adminTokenResponse.accessToken());
                softly.assertThat(response.refreshToken()).isEqualTo(adminTokenResponse.refreshToken());
            });
        }

        @Test
        void 입력한_이메일이_올바르지_않으면_예외가_발생한다() {
            // given
            adminRepository.save(관리자_생성());
            String invalidEmail = "invalidEmail";
            AdminLoginRequest adminLoginRequest = 관리자_로그인_요청(invalidEmail, PASSWORD);

            // when & then
            assertThatThrownBy(() -> adminAuthService.login(adminLoginRequest))
                    .isInstanceOf(AdminNotFoundException.class);
        }

        @Test
        void 입력한_비밀번호가_올바르지_않으면_예외가_발생한다() {
            // given
            adminRepository.save(관리자_생성());
            String invalidPassword = "invalidPassword";
            AdminLoginRequest adminLoginRequest = 관리자_로그인_요청(EMAIL, invalidPassword);

            // when & then
            assertThatThrownBy(() -> adminAuthService.login(adminLoginRequest))
                    .isInstanceOf(InvalidPasswordException.class);
        }
    }

    @Test
    void 리프레쉬_토큰으로_액세스_토큰을_재생성한다() {
        // given
        adminRepository.save(관리자_생성());
        Long expectedId = 1L;
        String refreshToken = "refreshToken";
        AdminAccessTokenResponse adminAccessTokenResponse = 관리자_액세스_토큰_생성_응답();
        when(adminTokenProvider.extract(refreshToken, ID, Long.class)).thenReturn(expectedId);
        when(adminTokenProvider.createAccessToken(any())).thenReturn(adminAccessTokenResponse.accessToken());

        // when
        AdminAccessTokenResponse response = adminAuthService.reGenerateAccessToken(refreshToken);

        // then
        assertThat(response.accessToken()).isEqualTo(adminAccessTokenResponse.accessToken());
    }
}
