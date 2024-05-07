package com.atwoz.survey.application.survey;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerNumberRangeException;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import com.atwoz.survey.exception.survey.exceptions.SurveyQuestionDuplicatedException;
import com.atwoz.survey.infrastructure.survey.SurveyFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_중복;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_두개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_번호_음수;
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
        SurveyCreateRequest request = 연애고사_필수_과목_질문_두개씩_생성_요청();

        // when
        Long id = surveyService.addSurvey(request);

        // then
        assertThat(id).isEqualTo(1L);
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
