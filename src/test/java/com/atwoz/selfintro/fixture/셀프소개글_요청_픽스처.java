package com.atwoz.selfintro.fixture;

import com.atwoz.selfintro.application.dto.SelfIntroCreateRequest;
import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.application.dto.SelfIntroUpdateRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 셀프소개글_요청_픽스처 {

    public static class 셀프소개글_생성_요청_픽스처 {

        private static final String CREATE_CONTENT = "안녕하세요~";

        public static SelfIntroCreateRequest 셀프소개글_생성_요청서() {
            return new SelfIntroCreateRequest(CREATE_CONTENT);
        }
    }

    public static class 셀프소개글_필터_요청_픽스처 {

        private static final int MIN_AGE = 20;
        private static final int MAX_AGE = 30;
        private static final boolean IS_ONLY_OPPOSITE_GENDER = false;
        private static final String CITY = "서울시";

        public static SelfIntroFilterRequest 셀프소개글_필터_요청서() {
            return new SelfIntroFilterRequest(
                    MIN_AGE,
                    MAX_AGE,
                    IS_ONLY_OPPOSITE_GENDER,
                    List.of(CITY)
            );
        }
    }

    public static class 셀프소개글_수정_요청_픽스처 {

        private static final String UPDATE_CONTENT = "반가워요~";


        public static SelfIntroUpdateRequest 셀프소개글_수정_요청서() {
            return new SelfIntroUpdateRequest(UPDATE_CONTENT);
        }

        public static SelfIntroUpdateRequest 셀프소개글_수정_요청서_내용(final String content) {
            return new SelfIntroUpdateRequest(content);
        }
    }
}
