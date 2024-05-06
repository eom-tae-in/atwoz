package com.atwoz.survey.domain.membersurvey;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberSurveyTest {

    @Test
    void 회원_설문_생성() {
        // given
        Long memberId = 1L;
        Long questionId = 1L;
        Long answerId = 1L;

        // when
        MemberSurvey memberSurvey = MemberSurvey.of(memberId, questionId, answerId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberSurvey.getMemberId()).isEqualTo(memberId);
            softly.assertThat(memberSurvey.getQuestionId()).isEqualTo(questionId);
            softly.assertThat(memberSurvey.getAnswerId()).isEqualTo(answerId);
        });
    }
}
