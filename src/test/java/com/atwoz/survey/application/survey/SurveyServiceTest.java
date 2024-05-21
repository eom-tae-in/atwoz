package com.atwoz.survey.application.survey;

import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyQuestionCreateRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerNumberRangeException;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import com.atwoz.survey.exception.survey.exceptions.SurveyQuestionDuplicatedException;
import com.atwoz.survey.infrastructure.survey.SurveyFakeRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_하나_생성;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_한개씩_전부_id_있음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyServiceTest {

    private SurveyService surveyService;
    private SurveyRepository surveyRepository;

    @BeforeEach
    void init() {
        surveyRepository = new SurveyFakeRepository();
        surveyService = new SurveyService(surveyRepository);
    }

    @Test
    void 연애고사_과목_등록() {
        // given
        SurveyCreateRequest request = 연애고사_필수_과목_하나_생성();
        Survey expectedSurvey = 연애고사_필수_과목_질문_한개씩_전부_id_있음();

        // when
        Survey survey = surveyService.addSurvey(request);

        // then
        assertThat(survey).usingRecursiveComparison()
                .isEqualTo(expectedSurvey);
    }

    @Nested
    class 연애고사_과목_등록_예외 {

        @Test
        void 같은_이름의_과목을_등록할_수_없다() {
            // given
            SurveyCreateRequest request = 연애고사_필수_과목_질문_두개씩_생성_요청();
            SurveyCreateRequest request2 = 연애고사_필수_과목_질문_두개씩_생성_요청();
            surveyService.addSurvey(request);

            // when & then
            assertThatThrownBy(() -> surveyService.addSurvey(request2))
                    .isInstanceOf(SurveyNameAlreadyExistException.class);
        }

        @Test
        void 연애고사_질문이_중복되면_안된다() {
            // given
            SurveyCreateRequest request = 연애고사_필수_과목_질문_중복();

            // when & then
            assertThatThrownBy(() -> surveyService.addSurvey(request))
                    .isInstanceOf(SurveyQuestionDuplicatedException.class);
        }

        @MethodSource("invalidSurveyAnswerRequests")
        @ParameterizedTest
        void 번호나_내용이_같은_답변은_생성할_수_없다(final int answerNumber1, final String answer1, final int answerNumber2, final String answer2) {
            // given
            SurveyCreateRequest request = new SurveyCreateRequest("과목", true, List.of(
                    new SurveyQuestionCreateRequest("질문", List.of(
                            SurveyAnswerCreateRequest.of(answerNumber1, answer1),
                            SurveyAnswerCreateRequest.of(answerNumber2, answer2)
                    ))
            ));

            // when & then
            assertThatThrownBy(() -> surveyService.addSurvey(request))
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
        void 연애고사_과목_답변_번호는_자연수여야_한다() {
            // given
            SurveyCreateRequest request = 연애고사_필수_과목_질문_번호_음수();

            // when & then
            assertThatThrownBy(() -> surveyService.addSurvey(request))
                    .isInstanceOf(SurveyAnswerNumberRangeException.class);
        }
    }
}
