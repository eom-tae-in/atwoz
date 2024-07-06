package com.atwoz.member.ui.auth.support;

import com.atwoz.member.exception.exceptions.auth.OAuthPlatformNotFountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OAuthPlatformTest {

    @DisplayName("등록된 인증 기관을 찾는다.")
    @Nested
    class findPlatform {

        @Test
        void 등록된_인증_기관을_문자열로_찾는다() {
            // given
            String oAuthPlatform = "kakao";

            // when
            OAuthPlatform platform = OAuthPlatform.findPlatform(oAuthPlatform);

            // then
            assertThat(platform).isEqualTo(OAuthPlatform.KAKAO);
        }

        @Test
        void 존재하지_않는_인증_기관을_찾는_경우_예외가_발생한다() {
            // given
            String notExistOAuthPlatForm = "oAuthPlatForm";

            // when & then
            assertThatThrownBy(() -> OAuthPlatform.findPlatform(notExistOAuthPlatForm))
                    .isInstanceOf(OAuthPlatformNotFountException.class);
        }
    }
}
