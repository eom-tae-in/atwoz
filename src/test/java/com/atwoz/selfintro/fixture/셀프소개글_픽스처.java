package com.atwoz.selfintro.fixture;

import com.atwoz.selfintro.domain.SelfIntro;

@SuppressWarnings("NonAsciiCharacters")
public class 셀프소개글_픽스처 {

    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final String DEFAULT_CONTENT = "안녕하세요~";

    public static SelfIntro 셀프소개글_생성() {
        return SelfIntro.createWith(DEFAULT_MEMBER_ID, DEFAULT_CONTENT);
    }

    public static SelfIntro 셀프소개글_생성_회원아이디(final Long memberId) {
        return SelfIntro.createWith(memberId, DEFAULT_CONTENT);
    }

    public static SelfIntro 셀프소개글_생성_셀프소개글아이디(final Long selfIntroId) {
        return SelfIntro.builder()
                .id(selfIntroId)
                .memberId(DEFAULT_MEMBER_ID)
                .content(DEFAULT_CONTENT)
                .build();
    }
}
