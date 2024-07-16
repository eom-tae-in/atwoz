package com.atwoz.member.fixture.selfintro;

import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;

public class SelfIntroResponseFixture {

    private static final Long SELF_INTRO_ID = 1L;
    private static final String CONTENT = "안녕하세요~";
    private static final String NICKNAME = "nickname";
    private static final String CITY = "서울시";
    private static final int AGE = 30;
    private static final int HEIGHT = 170;

    public static SelfIntroResponse 셀프_소개글_응답() {
        return new SelfIntroResponse(
                SELF_INTRO_ID,
                CONTENT,
                NICKNAME,
                CITY,
                AGE,
                HEIGHT
        );
    }

    public static SelfIntroResponse 셀프_소개글_응답(final Long selfIntroId,
                                              final String selfContent) {
        return new SelfIntroResponse(
                selfIntroId,
                selfContent,
                NICKNAME,
                CITY,
                AGE,
                HEIGHT
        );
    }
}
