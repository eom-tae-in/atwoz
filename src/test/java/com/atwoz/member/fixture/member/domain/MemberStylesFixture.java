package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.MemberStyles;
import com.atwoz.member.domain.member.profile.Style;
import java.util.List;

import static com.atwoz.member.fixture.member.dto.request.InternalStylesInitializeRequestFixture.내부_스타일_목록_초기화_요청_생성;

@SuppressWarnings("NonAsciiCharacters")
public class MemberStylesFixture {

    public static MemberStyles 회원_스타일_목록_생성() {
        MemberStyles memberStyles = new MemberStyles();
        memberStyles.initialize(내부_스타일_목록_초기화_요청_생성());

        return memberStyles;
    }

    public static MemberStyles 회원_스타일_목록_생성_스타일목록(final List<Style> styles) {
        MemberStyles memberStyles = new MemberStyles();
        memberStyles.initialize(내부_스타일_목록_초기화_요청_생성(styles));

        return memberStyles;
    }
}
