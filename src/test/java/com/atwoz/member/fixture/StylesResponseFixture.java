package com.atwoz.member.fixture;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.MemberStyle;
import com.atwoz.member.domain.member.profile.vo.Style;
import com.atwoz.member.infrastructure.member.dto.StylesResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class StylesResponseFixture {

    public static StylesResponse 스타일_응답서_요청(final Member member) {
        return StylesResponse.createWith(getStyles(member));
    }

    private static List<Style> getStyles(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getMemberStyles()
                .getStyles()
                .stream()
                .map(MemberStyle::getStyle)
                .toList();
    }
}
