package com.atwoz.survey.domain.survey;

import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerNumberRangeException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveyAnswerFixture.설문_답변_id있음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyAnswerTest {

    @Test
    void 설문_답변_생성() {
        // given
        Integer number = 1;
        String description = "답변";

        // when
        SurveyAnswer answer = SurveyAnswer.of(number, description);

        // then
        assertSoftly(softly -> {
            softly.assertThat(answer.getNumber()).isEqualTo(number);
            softly.assertThat(answer.getDescription()).isEqualTo(description);
        });
    }

    @Test
    void 설문_답변_id_비교() {
        // given
        SurveyAnswer surveyAnswer = 설문_답변_id있음();

        // when
        boolean isSame = surveyAnswer.isSame(1);

        // then
        assertThat(isSame).isTrue();
    }

    @Test
    void 동등성_비교() {
        // given
        SurveyAnswer surveyAnswer1 = 설문_답변_id있음();
        SurveyAnswer surveyAnswer2 = SurveyAnswer.of(1, "답변 1");

        // when
        boolean equals = surveyAnswer1.equals(surveyAnswer2);

        // then
        assertThat(equals).isTrue();
    }

    @Test
    void 답변_번호는_자연수여야_한다() {
        // given
        int number = -1;
        String answer = "답변 1";

        // when & then
        assertThatThrownBy(() -> SurveyAnswer.of(number, answer))
                .isInstanceOf(SurveyAnswerNumberRangeException.class);
    }
}
