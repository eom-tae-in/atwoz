package com.atwoz.survey.application.survey;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import com.atwoz.survey.infrastructure.survey.SurveyFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.설문_필수_질문_과목_두개씩;
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
    void 설문_등록() {
        // given
        SurveyCreateRequest request = 설문_필수_질문_과목_두개씩();

        // when
        Long id = surveyService.addSurvey(request);

        // then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void 같은_이름의_설문을_등록할_수_없다() {
        // given
        SurveyCreateRequest request = 설문_필수_질문_과목_두개씩();
        SurveyCreateRequest request2 = 설문_필수_질문_과목_두개씩();
        surveyService.addSurvey(request);

        // when & then
        assertThatThrownBy(() -> surveyService.addSurvey(request2))
                .isInstanceOf(SurveyNameAlreadyExistException.class);
    }
}
