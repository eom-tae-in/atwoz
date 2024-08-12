package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.update.InternalHobbiesUpdateRequest;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.fixture.member.domain.MemberHobbyFixture;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.MemberHobbyFixture.회원_취미_생성;

@SuppressWarnings("NonAsciiCharacters")
public class InternalHobbiesUpdateRequestFixture {

    public static InternalHobbiesUpdateRequest 내부_취미_목록_업데이트_요청_생성() {
        return new InternalHobbiesUpdateRequest(List.of(회원_취미_생성()));
    }

    public static InternalHobbiesUpdateRequest 내부_취미_목록_업데이트_요청_생성_취미목록(final List<Hobby> hobbies) {
        return new InternalHobbiesUpdateRequest(
                hobbies.stream()
                        .map(MemberHobbyFixture::회원_취미_생성_취미)
                        .toList()
        );
    }
}
