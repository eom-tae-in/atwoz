package com.atwoz.survey.domain;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyQuestionCreateRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyQuestionDuplicatedException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.설문_필수_질문_과목_두개씩;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyTest {

    @Test
    void 설문_과목_정상_생성() {
        // given
        SurveyCreateRequest request = 설문_필수_질문_과목_두개씩();

        // when
        Survey survey = Survey.createWith(request);

        // then
        assertSoftly(softly -> {
            softly.assertThat(survey.getName()).isEqualTo("설문 제목");
            softly.assertThat(survey.getRequired()).isTrue();
            softly.assertThat(survey.getQuestions()).hasSize(2);
        });
    }

    @Test
    void 과목_이름_수정() {
        // given
        SurveyCreateRequest request = 설문_필수_질문_과목_두개씩();
        Survey survey = Survey.createWith(request);

        // when
        survey.updateName("설문 제목 수정");

        // then
        assertThat(survey.getName()).isEqualTo("설문 제목 수정");
    }

    @Test
    void 과목_필수_여부_수정() {
        // given
        SurveyCreateRequest request = 설문_필수_질문_과목_두개씩();
        Survey survey = Survey.createWith(request);

        // when
        survey.updateRequired(false);

        // then
        assertThat(survey.getRequired()).isEqualTo(false);
    }

    @Nested
    class 설문_생성_예외 {

        @Test
        void 설문_질문이_중복되면_안_된다() {
            // given
            SurveyCreateRequest request = new SurveyCreateRequest("설문 제목", true, List.of(
                    new SurveyQuestionCreateRequest("질문1", List.of("답1", "답2")),
                    new SurveyQuestionCreateRequest("질문1", List.of("답1", "답2"))
            ));

            // when & then
            assertThatThrownBy(() -> Survey.createWith(request))
                    .isInstanceOf(SurveyQuestionDuplicatedException.class);
        }

        @Test
        void 설문_답변이_중복되면_안_된다() {
            // given
            SurveyCreateRequest request = new SurveyCreateRequest("설문 제목", true, List.of(
                    new SurveyQuestionCreateRequest("질문1", List.of("답1", "답1"))
            ));

            // when & then
            assertThatThrownBy(() -> Survey.createWith(request))
                    .isInstanceOf(SurveyAnswerDuplicatedException.class);
        }
    }
}
