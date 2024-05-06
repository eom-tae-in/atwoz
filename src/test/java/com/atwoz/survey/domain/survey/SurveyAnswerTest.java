package com.atwoz.survey.domain.survey;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveyAnswerFixture.설문_답변_id있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyAnswerTest {

    @Test
    void 설문_답변_생성() {
        // given
        String description = "답변";

        // when
        SurveyAnswer answer = SurveyAnswer.from(description);

        // then
        assertThat(answer.getDescription()).isEqualTo(description);
    }

    @Test
    void 설문_답변_id_비교() {
        // given
        SurveyAnswer surveyAnswer = 설문_답변_id있음();

        // when
        boolean isSame = surveyAnswer.isSame(1L);

        // then
        assertThat(isSame).isTrue();
    }

    @Test
    void 동등성_비교() {
        // given
        SurveyAnswer surveyAnswer1 = 설문_답변_id있음();
        SurveyAnswer surveyAnswer2 = SurveyAnswer.from("답변 1");

        // when
        boolean equals = surveyAnswer1.equals(surveyAnswer2);

        // then
        assertThat(equals).isTrue();
    }
}
