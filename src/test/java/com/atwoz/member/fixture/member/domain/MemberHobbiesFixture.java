package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.MemberHobbies;
import java.util.List;

import static com.atwoz.member.fixture.member.dto.request.InternalHobbiesInitializeRequestFixture.내부_취미_목록_초기화_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.InternalHobbiesInitializeRequestFixture.내부_취미_목록_초기화_요청_생성_취미목록;

@SuppressWarnings("NonAsciiCharacters")
public class MemberHobbiesFixture {

    public static MemberHobbies 회원_취미_목록_생성() {
        MemberHobbies memberHobbies = new MemberHobbies();
        memberHobbies.initialize(내부_취미_목록_초기화_요청_생성());

        return memberHobbies;
    }

    public static MemberHobbies 회원_취미_목록_생성_취미목록(final List<Hobby> hobbyCodes) {
        MemberHobbies memberHobbies = new MemberHobbies();
        memberHobbies.initialize(내부_취미_목록_초기화_요청_생성_취미목록(hobbyCodes));

        return memberHobbies;
    }
}
