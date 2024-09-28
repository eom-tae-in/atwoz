package com.atwoz.interview.fixture.memberinterview;

import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberInterviewSubmitRequestFixture {

    public static MemberInterviewSubmitRequest 회원_인터뷰_응시_요청() {
        return new MemberInterviewSubmitRequest("답변");
    }
}
