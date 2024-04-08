package com.atwoz.member.application.auth;

import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.infrastructure.auth.OAuthFakeConnectionManager;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoKeyWordRequest;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoResponse;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.atwoz.member.fixture.OAuthProviderFixture.인증_기관_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class OAuth2RequesterTest {

    @Mock
    private JsonMapper jsonMapper;
    private OAuth2Requester OAuth2Requester;

    @BeforeEach
    void init() {
        OAuth2Requester = new OAuth2Requester(new OAuthFakeConnectionManager(), jsonMapper);
    }

    @Test
    void 인증기관에_access_token을_요청한다() {
        // given
        String code = "testCode";
        OAuthProviderRequest oAuthProviderRequest = 인증_기관_생성();
        String expectedAccessToken = "accessToken";

        when(jsonMapper.getValueByKey(anyString(), anyString())).thenReturn(expectedAccessToken);

        // when
        String accessToken = OAuth2Requester.getAccessToken(code, oAuthProviderRequest);

        // then
        assertThat(accessToken).isEqualTo(expectedAccessToken);
    }

    @Test
    void 인증기관에_회원_정보를_요청한다() {
        // given
        String accessToken = "accessToken";
        OAuthProviderRequest oAuthProviderRequest = 인증_기관_생성();
        MemberInfoResponse expectedMemberInfo = new MemberInfoResponse("emial", "name");

        when(jsonMapper.extractMemberInfoFrom(anyString(), any(MemberInfoKeyWordRequest.class))).thenReturn(
                expectedMemberInfo);

        // when
        MemberInfoResponse memberInfo = OAuth2Requester.getMemberInfo(accessToken, oAuthProviderRequest);

        // then
        assertThat(memberInfo).isEqualTo(expectedMemberInfo);
    }
}
