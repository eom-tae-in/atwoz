package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.MemberHobby;

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성;

@SuppressWarnings("NonAsciiCharacters")
public class MemberHobbyFixture {

    public static MemberHobby 회원_취미_생성() {
        return MemberHobby.builder()
                .hobby(취미_생성())
                .build();
    }

    public static MemberHobby 회원_취미_생성_취미(final Hobby hobby) {
        return MemberHobby.builder()
                .hobby(hobby)
                .build();
    }
}
