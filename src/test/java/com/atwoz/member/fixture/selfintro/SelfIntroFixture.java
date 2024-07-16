package com.atwoz.member.fixture.selfintro;

import com.atwoz.member.domain.selfintro.SelfIntro;

public class SelfIntroFixture {

    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final String DEFAULT_CONTENT = "안녕하세요~";

    public static SelfIntro 셀프_소개글_생성_id_없음() {
        return SelfIntro.createWith(DEFAULT_MEMBER_ID, DEFAULT_CONTENT);
    }

    public static SelfIntro 셀프_소개글_생성_id_없음(final Long memberId) {
        return SelfIntro.createWith(memberId, DEFAULT_CONTENT);
    }

    public static SelfIntro 셀프_소개글_생성_id_있음(final Long id) {
        return SelfIntro.builder()
                .id(id)
                .memberId(DEFAULT_MEMBER_ID)
                .content(DEFAULT_CONTENT)
                .build();
    }
}
