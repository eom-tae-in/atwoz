package com.atwoz.selfintro.fixture;

import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;

@SuppressWarnings("NonAsciiCharacters")
public class 셀프소개글_응답_픽스처 {

    public static class 셀프소개글_페이징_조회_응답_픽스처 {

        private static final Long SELF_INTRO_ID = 1L;
        private static final String CONTENT = "안녕하세요~";
        private static final String NICKNAME = "nickname";
        private static final String CITY = "서울시";
        private static final int AGE = 30;
        private static final int HEIGHT = 170;

        public static SelfIntroQueryResponse 셀프소개글_응답() {
            return new SelfIntroQueryResponse(
                    SELF_INTRO_ID,
                    CONTENT,
                    NICKNAME,
                    CITY,
                    AGE,
                    HEIGHT
            );
        }

        public static SelfIntroQueryResponse 셀프소개글_응답_셀프소개글아이디_내용(final Long selfIntroId, final String content) {
            return new SelfIntroQueryResponse(
                    selfIntroId,
                    content,
                    NICKNAME,
                    CITY,
                    AGE,
                    HEIGHT
            );
        }
    }

}
