package com.atwoz.interview.fixture.memberinterview;

import com.atwoz.interview.application.memberinterview.dto.MemberInterviewUpdateRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberInterviewUpdateRequestFixture {

    public static MemberInterviewUpdateRequest 회원_인터뷰_수정_요청() {
        return new MemberInterviewUpdateRequest("수정 답변");
    }
}
