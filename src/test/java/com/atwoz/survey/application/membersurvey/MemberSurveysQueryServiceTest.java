package com.atwoz.survey.application.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.membersurvey.exceptions.MemberSurveysNotFoundException;
import com.atwoz.survey.infrastructure.membersurvey.MemberSurveysFakeRepository;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.survey.SurveyFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.survey.fixture.MemberSurveyQuestionResponseFixture.회원_연애고사_과목_조회_응답;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_30개_응시_저장_id;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_두개_응시_저장;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_30개씩;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_두개씩;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberSurveysQueryServiceTest {

    private MemberSurveysQueryService memberSurveysQueryService;
    private MemberSurveysRepository memberSurveysRepository;
    private SurveyRepository surveyRepository;

    @BeforeEach
    void init() {
        surveyRepository = new SurveyFakeRepository();
        memberSurveysRepository = new MemberSurveysFakeRepository();
        memberSurveysQueryService = new MemberSurveysQueryService(memberSurveysRepository);
    }

    @Test
    void 회원이_응시한_연애고사를_과목으로_조회한다() {
        // given
        Long memberId = 1L;
        Long surveyId = 1L;
        List<MemberSurveyQuestionResponse> questions = 회원_연애고사_과목_조회_응답();
        surveyRepository.save(연애고사_필수_과목_질문_두개씩());
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_두개_응시_저장());

        // when
        MemberSurveyResponse search = memberSurveysQueryService.findMemberSurvey(memberId, surveyId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(search.surveyId()).isEqualTo(surveyId);
            softly.assertThat(search.questions()).isEqualTo(questions);
        });
    }

    @Test
    void 없는_연애고사_과목이거나_작성하지_않은_과목을_조회하면_질문_목록이_비어있다() {
        // given
        Long memberId = 1L;
        Long surveyId = -1L;
        surveyRepository.save(연애고사_필수_과목_질문_두개씩());
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_두개_응시_저장());

        // when
        MemberSurveyResponse response = memberSurveysQueryService.findMemberSurvey(memberId, surveyId);

        // then
        assertThat(response.questions()).isEmpty();
    }

    @Test
    void 회원과_결과가_30개_이상_같은_다른_회원을_조회한다() {
        // given
        Long memberId = 1L;
        Long otherMemberId = 2L;

        surveyRepository.save(연애고사_필수_과목_질문_30개씩());
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id(memberId));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id(otherMemberId));

        // when
        List<Long> matchMembers = memberSurveysQueryService.findMatchMembers(memberId);

        // then
        assertThat(matchMembers.contains(otherMemberId)).isTrue();
    }

    @Nested
    class 연애고사_조회_예외 {

        @Test
        void 아예_연애고사를_작성하지_않았으면_내역_조회_시_예외가_발생한다() {
            // given
            Long memberId = 1L;
            Long surveyId = 1L;

            // when & then
            assertThatThrownBy(() -> memberSurveysQueryService.findMemberSurvey(memberId, surveyId))
                            .isInstanceOf(MemberSurveysNotFoundException.class);
        }

        @Test
        void 아예_연애고사를_작성하지_않았으면_매칭_시_예외가_발생한다() {
            // given
            Long memberId = 1L;
            Long otherMemberId = 2L;

            surveyRepository.save(연애고사_필수_과목_질문_30개씩());
            memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id(otherMemberId));

            // when & then
            assertThatThrownBy(() -> memberSurveysQueryService.findMatchMembers(memberId))
                    .isInstanceOf(MemberSurveysNotFoundException.class);
        }
    }
}
