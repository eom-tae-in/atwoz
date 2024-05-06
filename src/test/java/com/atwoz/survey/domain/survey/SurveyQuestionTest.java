package com.atwoz.survey.domain.survey;

import com.atwoz.survey.domain.survey.dto.SurveyQuestionComparisonRequest;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAnswerInvalidSubmitException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionNotSubmittedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.survey.fixture.SurveyQuestionFixture.설문_질문_답변_한개_id있음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyQuestionTest {

    @Test
    void 연애고사_질문_생성() {
        // given
        String description = "질문";
        List<String> answers = List.of("답변1", "답변2", "답변3");
        List<SurveyAnswer> surveyAnswers = List.of(
                SurveyAnswer.from("답변1"),
                SurveyAnswer.from("답변2"),
                SurveyAnswer.from("답변3")
        );

        // when
        SurveyQuestion surveyQuestion = SurveyQuestion.of(description, answers);

        // then
        assertSoftly(softly -> {
            softly.assertThat(surveyQuestion.getDescription()).isEqualTo(description);
            softly.assertThat(surveyQuestion.getAnswers()).isEqualTo(surveyAnswers);
        });
    }

    @Test
    void 연애고사_질문_응시() {
        // given
        SurveyQuestion surveyQuestion = 설문_질문_답변_한개_id있음();
        List<SurveyQuestionComparisonRequest> requests = List.of(
                new SurveyQuestionComparisonRequest(1L, 1L)
        );

        // when & then
        assertDoesNotThrow(() -> surveyQuestion.validateIsValidSubmitAnswer(requests));
    }

    @Test
    void 동등성_비교() {
        // given
        SurveyQuestion surveyQuestion = 설문_질문_답변_한개_id있음();
        SurveyQuestion anotherSurveyQuestion = SurveyQuestion.of("질문 1", List.of("답변 1"));

        // when
        boolean isSame = surveyQuestion.equals(anotherSurveyQuestion);

        // then
        assertThat(isSame).isTrue();
    }

    @Nested
    class 설문_질문_예외 {

        @Test
        void 중복된_답변은_작성할_수_없다() {
            // given
            String description = "질문";
            List<String> answers = List.of("답변1", "답변1", "답변3");

            // when & then
            assertThatThrownBy(() -> SurveyQuestion.of(description, answers))
                    .isInstanceOf(SurveyAnswerDuplicatedException.class);
        }

        @Test
        void 연애고사_질문을_응시할_때는_관련_질문이_있어야_한다() {
            // given
            SurveyQuestion surveyQuestion = 설문_질문_답변_한개_id있음();
            List<SurveyQuestionComparisonRequest> requests = List.of(
                    new SurveyQuestionComparisonRequest(2L, 1L),
                    new SurveyQuestionComparisonRequest(3L, 1L)
            );

            // when & then
            assertThatThrownBy(() -> surveyQuestion.validateIsValidSubmitAnswer(requests))
                    .isInstanceOf(SurveyQuestionNotSubmittedException.class);
        }

        @Test
        void 연애고사_질문을_응시할_때는_있는_답변으로만_작성해야_한다() {
            // given
            SurveyQuestion surveyQuestion = 설문_질문_답변_한개_id있음();
            List<SurveyQuestionComparisonRequest> requests = List.of(
                    new SurveyQuestionComparisonRequest(1L, 5L)
            );

            // when & then
            assertThatThrownBy(() -> surveyQuestion.validateIsValidSubmitAnswer(requests))
                    .isInstanceOf(SurveyAnswerInvalidSubmitException.class);
        }
    }
}
