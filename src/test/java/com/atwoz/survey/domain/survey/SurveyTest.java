package com.atwoz.survey.domain.survey;

import com.atwoz.survey.application.membersurvey.dto.SurveyQuestionSubmitRequest;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyQuestionCreateRequest;
import com.atwoz.survey.domain.survey.dto.SurveyAnswerCreateDto;
import com.atwoz.survey.domain.survey.dto.SurveyComparisonRequest;
import com.atwoz.survey.domain.survey.dto.SurveyCreateDto;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAnswerInvalidSubmitException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionNotSubmittedException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionSubmitSizeNotMatchException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerNumberRangeException;
import com.atwoz.survey.exception.survey.exceptions.SurveyQuestionDuplicatedException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_두개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_번호_음수;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_중복;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_한개씩_전부_id_있음;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyTest {

    @Test
    void 연애고사_과목_정상_생성() {
        // given
        SurveyCreateRequest request = 연애고사_필수_과목_질문_두개씩_생성_요청();
        List<SurveyQuestion> questions = List.of(
                SurveyQuestion.of("질문 내용1", List.of(
                        new SurveyAnswerCreateDto(1, "답변1"),
                        new SurveyAnswerCreateDto(2, "답변2"))
                ),
                SurveyQuestion.of("질문 내용2", List.of(
                        new SurveyAnswerCreateDto(1, "답변1"),
                        new SurveyAnswerCreateDto(2, "답변2"))
                )
        );

        // when
        Survey survey = Survey.from(SurveyCreateDto.from(request));

        // then
        assertSoftly(softly -> {
            softly.assertThat(survey.isSameName("과목 제목")).isTrue();
            softly.assertThat(survey.isRequired()).isTrue();
            softly.assertThat(survey.getQuestions()).isEqualTo(questions);
        });
    }

    @Test
    void 설문_응시_조건_검사_통과() {
        // given
        Survey survey = 연애고사_필수_과목_질문_한개씩_전부_id_있음();
        SurveyComparisonRequest request = SurveyComparisonRequest.from(
                new SurveySubmitRequest(1L, List.of(
                        new SurveyQuestionSubmitRequest(1L, 1)
                ))
        );

        // when & then
        assertDoesNotThrow(() -> survey.validateIsValidSubmitSurveyRequest(request));
    }

    @Nested
    class 연애고사_예외 {

        @Test
        void 연애고사_질문이_중복되면_안_된다() {
            // given
            SurveyCreateRequest request = 연애고사_필수_과목_질문_중복();

            // when & then
            assertThatThrownBy(() -> Survey.from(SurveyCreateDto.from(request)))
                    .isInstanceOf(SurveyQuestionDuplicatedException.class);
        }

        @MethodSource(value = "invalidSurveyAnswerRequests")
        @ParameterizedTest
        void 연애고사_답변_번호나_답변이_중복되면_안_된다(final int answerNumber1, final String answer1, final int answerNumber2, final String answer2) {
            // given
            SurveyCreateRequest request = new SurveyCreateRequest("설문 제목", true, List.of(
                    new SurveyQuestionCreateRequest("질문1", List.of(
                            SurveyAnswerCreateRequest.of(answerNumber1, answer1),
                            SurveyAnswerCreateRequest.of(answerNumber2, answer2)
                    ))
            ));

            // when & then
            assertThatThrownBy(() -> Survey.from(SurveyCreateDto.from(request)))
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
        void 연애고사_답변_번호는_자연수여야_한다() {
            // given
            SurveyCreateDto request = SurveyCreateDto.from(연애고사_필수_과목_질문_번호_음수());

            // when & then
            assertThatThrownBy(() -> Survey.from(request))
                    .isInstanceOf(SurveyAnswerNumberRangeException.class);
        }

        @MethodSource(value = "invalidSurveyQuestionRequests")
        @ParameterizedTest
        void 연애고사_응시_질문_id_갯수가_다르거나_id가_중복되면_예외가_발생한다(final Long questionId1, final Long questionId2) {
            // given
            Survey survey = 연애고사_필수_과목_질문_한개씩_전부_id_있음();
            SurveyComparisonRequest request = SurveyComparisonRequest.from(
                    new SurveySubmitRequest(1L, List.of(
                            new SurveyQuestionSubmitRequest(questionId1, 1),
                            new SurveyQuestionSubmitRequest(questionId2, 1)
                    ))
            );

            // when & then
            assertThatThrownBy(() -> survey.validateIsValidSubmitSurveyRequest(request))
                    .isInstanceOf(SurveyQuestionSubmitSizeNotMatchException.class);
        }

        static Stream<Arguments> invalidSurveyQuestionRequests() {
            return Stream.of(
                    Arguments.arguments(1L, 1L),
                    Arguments.arguments(1L, 2L)
            );
        }

        @Test
        void 연애고사_응시_질문_id가_없으면_예외가_발생한다() {
            // given
            Survey survey = 연애고사_필수_과목_질문_한개씩_전부_id_있음();
            SurveyComparisonRequest request = SurveyComparisonRequest.from(
                    new SurveySubmitRequest(1L, List.of(
                            new SurveyQuestionSubmitRequest(5L, 1)
                    ))
            );

            // when & then
            assertThatThrownBy(() -> survey.validateIsValidSubmitSurveyRequest(request))
                    .isInstanceOf(SurveyQuestionNotSubmittedException.class);
        }

        @Test
        void 연애고사에_있는_질문의_답변이_아닌_답변을_내면_예외가_발생한다() {
            // given
            Survey survey = 연애고사_필수_과목_질문_한개씩_전부_id_있음();
            SurveyComparisonRequest request = SurveyComparisonRequest.from(
                    new SurveySubmitRequest(1L, List.of(
                            new SurveyQuestionSubmitRequest(1L, 5)
                    ))
            );

            // when & then
            assertThatThrownBy(() -> survey.validateIsValidSubmitSurveyRequest(request))
                    .isInstanceOf(SurveyAnswerInvalidSubmitException.class);
        }
    }
}
