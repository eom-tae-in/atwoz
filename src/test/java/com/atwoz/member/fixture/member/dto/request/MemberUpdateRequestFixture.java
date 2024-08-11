package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import java.util.List;

import static com.atwoz.member.fixture.member.dto.request.ProfileUpdateRequestFixture.프로필_업데이트_요청;
import static com.atwoz.member.fixture.member.dto.request.ProfileUpdateRequestFixture.프로필_업데이트_요청_취미코드목록_스타일코드목록;

@SuppressWarnings("NonAsciiCharacters")
public class MemberUpdateRequestFixture {

    private static final String DEFAULT_NICKNAME = "newNickname";

    public static MemberUpdateRequest 회원_업데이트_요청() {
        return new MemberUpdateRequest(
                프로필_업데이트_요청(),
                DEFAULT_NICKNAME
        );
    }

    public static MemberUpdateRequest 회원_업데이트_요청_닉네임(final String nickname) {
        return new MemberUpdateRequest(
                프로필_업데이트_요청(),
                nickname
        );
    }

    public static MemberUpdateRequest 회원_업데이트_요청_취미코드목록_스타일코드목록(final List<String> hobbyCodes,
                                                                final List<String> styleCodes) {
        return new MemberUpdateRequest(
                프로필_업데이트_요청_취미코드목록_스타일코드목록(hobbyCodes, styleCodes),
                DEFAULT_NICKNAME
        );
    }
}
