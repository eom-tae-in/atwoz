package com.atwoz.member.ui.auth.support.auth;

import com.atwoz.member.domain.auth.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingResponseWrapper;

@RequiredArgsConstructor
@Component
public class AuthenticationExtractor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String HEADER_SPLIT_DELIMITER = " ";
    private static final String TOKEN = "token";
    private static final int TOKEN_TYPE_INDEX = 0;
    private static final int TOKEN_VALUE_INDEX = 1;
    private static final int VALID_HEADER_SPLIT_LENGTH = 2;

    private final JsonMapper jsonMapper;

    public Optional<String> extractFromRequest(final HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(header)) {
            return Optional.empty();
        }

        return extractFromHeader(header.split(HEADER_SPLIT_DELIMITER));
    }

    public Optional<String> extractFromHeader(final String[] headerParts) {
        if (headerParts.length == VALID_HEADER_SPLIT_LENGTH &&
                headerParts[TOKEN_TYPE_INDEX].equals(BEARER)) {
            return Optional.ofNullable(headerParts[TOKEN_VALUE_INDEX]);
        }

        return Optional.empty();
    }

    public String extractFromResponse(final HttpServletResponse response) {
        ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
        String responseBody = getResponseBody(responseWrapper);

        return jsonMapper.getValueByKey(responseBody, TOKEN);
    }

    private String getResponseBody(final ContentCachingResponseWrapper responseWrapper) {
        return new String(responseWrapper.getContentAsByteArray());
    }
}

