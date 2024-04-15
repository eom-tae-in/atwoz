package com.atwoz.member.ui.auth.support.auth;

import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.exception.exceptions.auth.InvalidJsonKeyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.ContentCachingResponseWrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthenticationExtractorTest {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final ContentCachingResponseWrapper response = mock(ContentCachingResponseWrapper.class);
    private final JsonMapper jsonMapper = mock(JsonMapper.class);
    private AuthenticationExtractor authenticationExtractor;

    @BeforeEach
    void setup() {
        authenticationExtractor = new AuthenticationExtractor(jsonMapper);
    }


    @Nested
    class 요청에서_토큰_추출 {

        @Test
        void 토큰이_정상적으로_조회된다() {
            // given
            String expectedResponseToken = BEARER + " tokenSignature";
            when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn(expectedResponseToken);

            // when
            Optional<String> result = authenticationExtractor.extractFromRequest(request);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result).isEqualTo(Optional.of("tokenSignature"));
            });
        }

        @Test
        void 토큰_헤더가_없다면_빈_값이_반환된다() {
            // given
            when(request.getHeader(AUTHORIZATION_HEADER)).thenReturn("InvalidType token");

            // when
            Optional<String> result = authenticationExtractor.extractFromRequest(request);

            // then
            assertThat(result).isEmpty();
        }
    }

    @Nested
    class 응답에서_토큰_추출 {

        @Test
        void 토큰이_정상적으로_조회된다() throws JsonProcessingException {
            // given
            String tokenJson = "{\"token\":\"validToken\"}";
            when(response.getContentAsByteArray()).thenReturn(tokenJson.getBytes());
            when(jsonMapper.getValueByKey(tokenJson, "token")).thenReturn("validToken");

            // when
            String extractedToken = authenticationExtractor.extractFromResponse(response);

            // then
            assertThat(extractedToken).isEqualTo("validToken");
        }

        @Test
        void 응답_body에_토큰의_정보가_없으면_예외가_발생한다() {
            // given
            String invalidToken = "invalidToken";
            when(response.getContentAsByteArray()).thenReturn(invalidToken.getBytes());
            when(jsonMapper.getValueByKey(invalidToken, "token")).thenThrow(InvalidJsonKeyException.class);

            // when & then
            assertThatThrownBy(() -> authenticationExtractor.extractFromResponse(response))
                    .isInstanceOf(InvalidJsonKeyException.class);
        }
    }
}
