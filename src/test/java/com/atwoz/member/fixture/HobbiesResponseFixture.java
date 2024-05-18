package com.atwoz.member.fixture;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.MemberHobby;
import com.atwoz.member.domain.member.profile.vo.Hobby;
import com.atwoz.member.infrastructure.member.dto.HobbiesResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class HobbiesResponseFixture {

    public static HobbiesResponse 취미_응답서_요청(final Member member) {
        return HobbiesResponse.createWith(getHobbies(member));
    }

    private static List<Hobby> getHobbies(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getMemberHobbies()
                .getHobbies()
                .stream()
                .map(MemberHobby::getHobby)
                .toList();
    }
}
