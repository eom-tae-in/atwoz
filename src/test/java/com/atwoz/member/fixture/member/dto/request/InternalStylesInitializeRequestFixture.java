package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.initial.InternalStylesInitializeRequest;
import com.atwoz.member.domain.member.profile.Style;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.MemberStyleFixture.회원_스타일_생성;

@SuppressWarnings("NonAsciiCharacters")
public class InternalStylesInitializeRequestFixture {

    public static InternalStylesInitializeRequest 내부_스타일_목록_초기화_요청_생성() {
        return new InternalStylesInitializeRequest(List.of(회원_스타일_생성()));
    }

    public static InternalStylesInitializeRequest 내부_스타일_목록_초기화_요청_생성(final List<Style> styles) {
        return InternalStylesInitializeRequest.from(styles);
    }
}
