package com.atwoz.admin.ui.auth.support;

import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AdminAuthenticationContextTest {

    private AdminAuthenticationContext adminAuthenticationContext;

    @BeforeEach
    void setup() {
        adminAuthenticationContext = new AdminAuthenticationContext();
    }

    @Test
    void admin_id를_반환한다() {
        // given
        adminAuthenticationContext.setAuthentication(1L);

        // when
        Long result = adminAuthenticationContext.getPrincipal();

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void admin_id가_없다면_예외를_발생한다() {
        // given
        adminAuthenticationContext.setAuthentication(null);

        // when & then
        assertThatThrownBy(() -> adminAuthenticationContext.getPrincipal())
                .isInstanceOf(AdminLoginInvalidException.class);
    }
}
