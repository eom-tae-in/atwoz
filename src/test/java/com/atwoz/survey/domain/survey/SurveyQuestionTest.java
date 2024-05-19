package com.atwoz.survey.domain.survey;

import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;
import com.atwoz.survey.domain.survey.dto.SurveyAnswerCreateDto;
import com.atwoz.survey.domain.survey.dto.SurveyQuestionComparisonRequest;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAnswerInvalidSubmitException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionNotSubmittedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerNumberRangeException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;
import static com.atwoz.survey.fixture.SurveyQuestionFixture.연애고사_질문_답변_한개_id_있음;
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
        List<SurveyAnswerCreateDto> surveyAnswers = List.of(
                SurveyAnswerCreateDto.from(new SurveyAnswerCreateRequest(1, "답변1")),
                SurveyAnswerCreateDto.from(new SurveyAnswerCreateRequest(2, "답변2")),
                SurveyAnswerCreateDto.from(new SurveyAnswerCreateRequest(3, "답변3"))
        );
        List<SurveyAnswer> answers = List.of(
                SurveyAnswer.of(1, "답변1"),
                SurveyAnswer.of(2, "답변2"),
                SurveyAnswer.of(3, "답변3")
        );

        // when
        SurveyQuestion surveyQuestion = SurveyQuestion.of(description, surveyAnswers);

        // then
        assertSoftly(softly -> {
            softly.assertThat(surveyQuestion.getDescription()).isEqualTo(description);
            softly.assertThat(surveyQuestion.getAnswers()).isEqualTo(answers);
        });
    }

    @Test
    void 연애고사_질문_응시() {
        // given
        SurveyQuestion surveyQuestion = 연애고사_질문_답변_한개_id_있음();
        List<SurveyQuestionComparisonRequest> requests = List.of(
                new SurveyQuestionComparisonRequest(1L, 1)
        );

        // when & then
        assertDoesNotThrow(() -> surveyQuestion.validateIsValidSubmitAnswer(requests));
    }

    @Test
    void 동등성_비교() {
        // given
        SurveyQuestion surveyQuestion = 연애고사_질문_답변_한개_id_있음();
        SurveyQuestion anotherSurveyQuestion = SurveyQuestion.of("질문 1", List.of(
                new SurveyAnswerCreateDto(1, "답변 1"))
        );

        // when
        boolean isSame = surveyQuestion.equals(anotherSurveyQuestion);

        // then
        assertThat(isSame).isTrue();
    }

    @Nested
    class 연애고사_질문_예외 {

        @MethodSource(value = "invalidSurveyAnswerRequests")
        @ParameterizedTest
        void 번호나_내용이_같은_답변은_생성할_수_없다(final int answerNumber1, final String answer1, final int answerNumber2, final String answer2) {
            // given
            String description = "질문";
            List<SurveyAnswerCreateDto> answers = List.of(
                    new SurveyAnswerCreateDto(answerNumber1, answer1),
                    new SurveyAnswerCreateDto(answerNumber2, answer2),
                    new SurveyAnswerCreateDto(3, "답변 3")
            );

            // when & then
            assertThatThrownBy(() -> SurveyQuestion.of(description, answers))
                    .isInstanceOf(SurveyAnswerDuplicatedException.class);
        }

        static Stream<Arguments> invalidSurveyAnswerRequests() {
            return Stream.of(
                    Arguments.arguments(1, "답1", 1, "답2"),
                    Arguments.arguments(1, "답1", 2, "답1"),
                    Arguments.arguments(1, "답1", 1, "답1")
            );
        }

        @Test
        void 답변_번호가_자연수가_아니면_예외가_발생한다() {
            // given
            String description = "질문";
            List<SurveyAnswerCreateDto> answers = List.of(
                    new SurveyAnswerCreateDto(-1, "답변 1")
            );

            // when & then
            assertThatThrownBy(() -> SurveyQuestion.of(description, answers))
                    .isInstanceOf(SurveyAnswerNumberRangeException.class);
        }

        @Test
        void 연애고사_질문을_응시할_때는_관련_질문이_있어야_한다() {
            // given
            SurveyQuestion surveyQuestion = 연애고사_질문_답변_한개_id_있음();
            List<SurveyQuestionComparisonRequest> requests = List.of(
                    new SurveyQuestionComparisonRequest(2L, 1),
                    new SurveyQuestionComparisonRequest(3L, 1)
            );

            // when & then
            assertThatThrownBy(() -> surveyQuestion.validateIsValidSubmitAnswer(requests))
                    .isInstanceOf(SurveyQuestionNotSubmittedException.class);
        }

        @Test
        void 연애고사_질문을_응시할_때는_있는_답변으로만_작성해야_한다() {
            // given
            SurveyQuestion surveyQuestion = 연애고사_질문_답변_한개_id_있음();
            List<SurveyQuestionComparisonRequest> requests = List.of(
                    new SurveyQuestionComparisonRequest(1L, 5)
            );

            // when & then
            assertThatThrownBy(() -> surveyQuestion.validateIsValidSubmitAnswer(requests))
                    .isInstanceOf(SurveyAnswerInvalidSubmitException.class);
        }
    }
}
