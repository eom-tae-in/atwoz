package com.atwoz.member.fixture.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;

import static com.atwoz.member.fixture.member.HobbiesResponseFixture.취미_응답서_요청;
import static com.atwoz.member.fixture.member.MemberProfileResponseFixture.회원_프로필_응답서_요청;
import static com.atwoz.member.fixture.member.StylesResponseFixture.스타일_응답서_요청;

@SuppressWarnings("NonAsciiCharacters")
public class MemberResponseFixture {

    public static MemberResponse 회원_정보_응답서_요청(final Member member) {
        return new MemberResponse(회원_프로필_응답서_요청(member), 취미_응답서_요청(member), 스타일_응답서_요청(member));
    }
}
