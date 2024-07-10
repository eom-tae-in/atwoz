package com.atwoz.admin.infrastructure.auth;

import com.atwoz.admin.domain.admin.AdminRefreshToken;
import com.atwoz.helper.IntegrationHelper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.admin.fixture.AdminRefreshTokenFixture.관리자_리프레쉬_토큰_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AdminRedisRefreshTokenRepositoryTest extends IntegrationHelper {

    @Autowired
    private AdminRedisRefreshTokenRepository adminRedisRefreshTokenRepository;


    @Test
    void 관리자_토큰_저장_및_조회() {
        // given
        AdminRefreshToken adminRefreshToken = 관리자_리프레쉬_토큰_생성();

        // when
        adminRedisRefreshTokenRepository.save(adminRefreshToken);
        Optional<AdminRefreshToken> foundAdminRefreshToken =
                adminRedisRefreshTokenRepository.findById(adminRefreshToken.refreshToken());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundAdminRefreshToken).isNotEmpty();
            AdminRefreshToken refreshToken = foundAdminRefreshToken.get();
            softly.assertThat(refreshToken.memberId()).isEqualTo(adminRefreshToken.memberId());
            softly.assertThat(refreshToken.refreshToken()).isEqualTo(adminRefreshToken.refreshToken());
        });
    }

    @Test
    void 관리자_토큰_삭제() {
        // given
        AdminRefreshToken adminRefreshToken = 관리자_리프레쉬_토큰_생성();
        adminRedisRefreshTokenRepository.save(adminRefreshToken);

        // when
        adminRedisRefreshTokenRepository.delete(adminRefreshToken.refreshToken());
        Optional<AdminRefreshToken> after = adminRedisRefreshTokenRepository.findById(adminRefreshToken.refreshToken());

        // then
        assertThat(after).isEmpty();
    }
}
