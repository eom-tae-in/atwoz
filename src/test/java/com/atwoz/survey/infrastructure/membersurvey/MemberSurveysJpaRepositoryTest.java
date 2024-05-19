package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.dto.SurveySubmitCreateDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import static com.atwoz.survey.fixture.SurveySubmitCreateDtoFixture.필수_과목_질문_두개_제출_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberSurveysJpaRepositoryTest {

    @Autowired
    private MemberSurveysJpaRepository memberSurveysJpaRepository;

    @Test
    void 회원_연애고사_응시() {
        // given
        Long memberId = 1L;
        MemberSurveys memberSurveys = MemberSurveys.createWithMemberId(memberId);
        List<SurveySubmitCreateDto> requests = 필수_과목_질문_두개_제출_요청();
        memberSurveys.submitSurveys(requests);

        // when
        MemberSurveys savedMemberSurveys = memberSurveysJpaRepository.save(memberSurveys);

        // then
        assertThat(memberSurveys).usingRecursiveComparison()
                .isEqualTo(savedMemberSurveys);
    }

    @Test
    void 회원_연애고사_조회() {
        // given
        Long memberId = 1L;
        MemberSurveys memberSurveys = MemberSurveys.createWithMemberId(memberId);
        MemberSurveys savedMemberSurveys = memberSurveysJpaRepository.save(memberSurveys);

        // when
        Optional<MemberSurveys> foundMemberSurveys = memberSurveysJpaRepository.findByMemberId(memberId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundMemberSurveys).isPresent();
            softly.assertThat(foundMemberSurveys.get()).usingRecursiveComparison()
                    .isEqualTo(savedMemberSurveys);
        });
    }
}
