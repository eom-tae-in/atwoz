package com.atwoz.member.ui.auth.support;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OAuthPropertiesTest extends IntegrationHelper {

    @Autowired
    private OAuthProperties oAuthProperties;

    @Test
    void 인증기관_이름으로_OAuthProvider를_찾아_반환한다() {
        // given
        OAuthPlatform oAuthPlatform = OAuthPlatform.KAKAO;

        // when
        OAuthProviderRequest oAuthProviderRequest = oAuthProperties.findByProviderName("kakao");

        // then
        assertThat(oAuthProviderRequest).usingRecursiveComparison()
                .isEqualTo(oAuthProperties.getProperties().get(oAuthPlatform));
    }
}
