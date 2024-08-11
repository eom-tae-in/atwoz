package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.initial.InternalHobbiesInitializeRequest;
import com.atwoz.member.domain.member.profile.Hobby;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.MemberHobbyFixture.회원_취미_생성;

@SuppressWarnings("NonAsciiCharacters")
public class InternalHobbiesInitializeRequestFixture {

    public static InternalHobbiesInitializeRequest 내부_취미_목록_초기화_요청_생성() {
        return new InternalHobbiesInitializeRequest(List.of(회원_취미_생성()));
    }

    public static InternalHobbiesInitializeRequest 내부_취미_목록_초기화_요청_생성_취미목록(final List<Hobby> hobbies) {
        return InternalHobbiesInitializeRequest.from(hobbies);
    }
}
