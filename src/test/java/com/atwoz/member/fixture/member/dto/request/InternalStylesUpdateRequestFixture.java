package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.update.InternalStylesUpdateRequest;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.fixture.member.domain.MemberStyleFixture;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.MemberStyleFixture.회원_스타일_생성;

@SuppressWarnings("NonAsciiCharacters")
public class InternalStylesUpdateRequestFixture {

    public static InternalStylesUpdateRequest 내부_스타일_목록_업데이트_요청_생성() {
        return new InternalStylesUpdateRequest(List.of(회원_스타일_생성()));
    }

    public static InternalStylesUpdateRequest 내부_스타일_목록_업데이트_요청_생성_스타일목록(final List<Style> styles) {
        return new InternalStylesUpdateRequest(
                styles.stream()
                        .map(MemberStyleFixture::회원_스타일_생성_스타일)
                        .toList()
        );
    }
}
