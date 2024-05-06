package com.atwoz.survey.infrastructure.survey;

import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.fixture.SurveyFixture;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.survey.fixture.SurveyFixture.설문_필수_질문_과목_두개씩;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
public class SurveyJpaRepositoryTest {

    @Autowired
    private SurveyJpaRepository surveyJpaRepository;

    @Test
    void 설문_과목_등록() {
        // given
        Survey survey = 설문_필수_질문_과목_두개씩();

        // when
        Survey saveSurvey = surveyJpaRepository.save(survey);

        // then
        assertThat(saveSurvey).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(survey);
    }
}
