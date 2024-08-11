package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.MemberStyle;
import com.atwoz.member.domain.member.profile.Style;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성;

@SuppressWarnings("NonAsciiCharacters")
public class MemberStyleFixture {

    public static MemberStyle 회원_스타일_생성() {
        return MemberStyle.builder()
                .style(스타일_생성())
                .build();
    }

    public static MemberStyle 회원_스타일_생성_스타일(final Style style) {
        return MemberStyle.builder()
                .style(style)
                .build();
    }
}
