package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.initial.MemberInitializeRequest;
import java.util.List;

import static com.atwoz.member.fixture.member.dto.request.ProfileInitializeRequestFixture.프로필_초기화_요청;
import static com.atwoz.member.fixture.member.dto.request.ProfileInitializeRequestFixture.프로필_초기화_요청_취미코드목록_스타일코드목록;

@SuppressWarnings("NonAsciiCharacters")
public class MemberInitializeRequestFixture {

    private static final String DEFAULT_NICKNAME = "nickname";
    private static final String DEFAULT_RECOMMENDER_NICKNAME = null;

    public static MemberInitializeRequest 회원_초기화_요청() {
        return new MemberInitializeRequest(
                프로필_초기화_요청(),
                DEFAULT_NICKNAME,
                DEFAULT_RECOMMENDER_NICKNAME
        );
    }

    public static MemberInitializeRequest 회원_초기화_요청_닉네임(final String nickname) {
        return new MemberInitializeRequest(
                프로필_초기화_요청(),
                nickname,
                DEFAULT_RECOMMENDER_NICKNAME
        );
    }

    public static MemberInitializeRequest 회원_초기화_요청_닉네임_취미코드목록_스타일코드목록(
            final String nickname,
            final List<String> hobbyCodes,
            final List<String> styleCodes
    ) {
        return new MemberInitializeRequest(
                프로필_초기화_요청_취미코드목록_스타일코드목록(hobbyCodes, styleCodes),
                nickname,
                DEFAULT_RECOMMENDER_NICKNAME
        );
    }

    public static MemberInitializeRequest 회원_초기화_요청_닉네임_추천인_닉네임(final String nickname,
                                                                final String recommenderNickname) {
        return new MemberInitializeRequest(
                프로필_초기화_요청(),
                nickname,
                recommenderNickname
        );
    }
}
