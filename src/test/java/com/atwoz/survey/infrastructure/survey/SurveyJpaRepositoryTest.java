package com.atwoz.survey.infrastructure.survey;

import com.atwoz.survey.domain.survey.Survey;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_선택_과목_질문_두개씩;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_두개씩;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
public class SurveyJpaRepositoryTest {

    @Autowired
    private SurveyJpaRepository surveyJpaRepository;

    @Test
    void 연애고사_과목_등록() {
        // given
        Survey survey = 연애고사_필수_과목_질문_두개씩();

        // when
        Survey saveSurvey = surveyJpaRepository.save(survey);

        // then
        assertThat(saveSurvey).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(survey);
    }

    @Test
    void 연애고사_과목_존재여부_검사() {
        // given
        Survey survey = 연애고사_필수_과목_질문_두개씩();
        surveyJpaRepository.save(survey);

        // when
        boolean find = surveyJpaRepository.existsByName(survey.getName());

        // then
        assertThat(find).isTrue();
    }

    @Test
    void 연애고사_id로_과목_조회() {
        // given
        Survey survey = 연애고사_필수_과목_질문_두개씩();
        Survey savedSurvey = surveyJpaRepository.save(survey);

        // when
        Optional<Survey> searchSurvey = surveyJpaRepository.findById(savedSurvey.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(searchSurvey).isPresent();
            softly.assertThat(searchSurvey.get()).isEqualTo(savedSurvey);
        });
    }

    @Test
    void 필수_연애고사_조회() {
        // given
        Survey survey = 연애고사_필수_과목_질문_두개씩();
        Survey notRequiredSurvey = 연애고사_선택_과목_질문_두개씩();
        Survey savedRequiredSurvey = surveyJpaRepository.save(survey);
        Survey savedNotRequiredSurvey = surveyJpaRepository.save(notRequiredSurvey);

        // when
        List<Long> requiredSurveyIds = surveyJpaRepository.findAllRequiredSurveyIds();

        // then
        assertSoftly(softly -> {
            softly.assertThat(requiredSurveyIds.contains(savedRequiredSurvey.getId())).isTrue();
            softly.assertThat(requiredSurveyIds.contains(savedNotRequiredSurvey.getId())).isFalse();
        });
    }
}
