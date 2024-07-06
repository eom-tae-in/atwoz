package com.atwoz.admin.domain.admin;

import com.atwoz.admin.domain.admin.vo.AdminStatus;
import com.atwoz.admin.domain.admin.vo.Authority;
import com.atwoz.admin.domain.admin.vo.Department;
import com.atwoz.admin.exception.exceptions.InvalidPasswordException;
import com.atwoz.admin.exception.exceptions.PasswordMismatchException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.admin.fixture.AdminFixture.관리자_생성;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AdminTest {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "010-1234-5678";

    @Nested
    class 관리자_생성 {

        @Test
        void 입력이_올바르면_관리자를_생성한다() {
            // when
            Admin admin = Admin.createWith(
                    EMAIL,
                    PASSWORD,
                    CONFIRM_PASSWORD,
                    NAME,
                    PHONE_NUMBER
            );

            // then
            assertSoftly(softly -> {
                softly.assertThat(admin.getEmail()).isEqualTo(EMAIL);
                softly.assertThat(admin.getPassword()).isEqualTo(PASSWORD);
                softly.assertThat(admin.getName()).isEqualTo(NAME);
                softly.assertThat(admin.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
                softly.assertThat(admin.getAuthority()).isEqualTo(Authority.ADMIN);
                softly.assertThat(admin.getDepartment()).isEqualTo(Department.OPERATION);
                softly.assertThat(admin.getAdminStatus()).isEqualTo(AdminStatus.PENDING);
            });

        }

        @Test
        void 회원_가입시_입력한_비밀번호와_확인_비밀번호가_다르면_예외가_발생한다() {
            // given
            String invalidConfirmPassword = "invalidConfirmPassword";

            // when & then
            assertThatThrownBy(() -> Admin.createWith(
                    EMAIL,
                    PASSWORD,
                    invalidConfirmPassword,
                    NAME,
                    PHONE_NUMBER
            )).isInstanceOf(PasswordMismatchException.class);
        }
    }

    @Test
    void 로그인시_입력한_비밀번호가_관리자의_비밀번호와_다르면_예외가_발생한다() {
        // given
        Admin admin = 관리자_생성();
        String invalidPassword = "invalidPassword";

        // when & then
        assertThatThrownBy(() -> admin.validatePassword(invalidPassword))
                .isInstanceOf(InvalidPasswordException.class);
    }
}
