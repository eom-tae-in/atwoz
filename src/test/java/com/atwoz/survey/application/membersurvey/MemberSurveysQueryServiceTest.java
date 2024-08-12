package com.atwoz.survey.application.membersurvey;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.membersurvey.exceptions.MemberSurveysNotFoundException;
import com.atwoz.survey.infrastructure.membersurvey.MemberSurveysFakeRepository;
import com.atwoz.survey.infrastructure.membersurvey.ZeroIndexFixSoulmatePolicy;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import com.atwoz.survey.infrastructure.survey.SurveyFakeRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_성별;
import static com.atwoz.survey.fixture.MemberSurveyQuestionResponseFixture.회원_연애고사_과목_조회_응답;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_30개_응시_저장_id;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_30개_응시_저장_id_과목_답안;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_두개_응시_저장;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_30개씩;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_두개씩;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberSurveysQueryServiceTest {

    private MemberRepository memberRepository;
    private SurveyRepository surveyRepository;
    private MemberSurveysRepository memberSurveysRepository;
    private MemberSurveysQueryService memberSurveysQueryService;

    @BeforeEach
    void init() {
        memberRepository = new MemberFakeRepository();
        surveyRepository = new SurveyFakeRepository();
        memberSurveysRepository = new MemberSurveysFakeRepository(memberRepository);
        memberSurveysQueryService = new MemberSurveysQueryService(memberSurveysRepository, new ZeroIndexFixSoulmatePolicy());
    }

    @Test
    void 회원이_응시한_연애고사를_과목으로_조회한다() {
        // given
        Member member = memberRepository.save(회원_생성());
        List<MemberSurveyQuestionResponse> questions = 회원_연애고사_과목_조회_응답();
        Survey survey = surveyRepository.save(연애고사_필수_과목_질문_두개씩());
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_두개_응시_저장());

        // when
        MemberSurveyResponse search = memberSurveysQueryService.findMemberSurvey(member.getId(), survey.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(search.surveyId()).isEqualTo(survey.getId());
            softly.assertThat(search.questions()).isEqualTo(questions);
        });
    }

    @Test
    void 없는_연애고사_과목이거나_작성하지_않은_과목을_조회하면_질문_목록이_비어있다() {
        // given
        Member member = memberRepository.save(회원_생성());
        Long surveyId = -1L;
        surveyRepository.save(연애고사_필수_과목_질문_두개씩());
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_두개_응시_저장());

        // when
        MemberSurveyResponse response = memberSurveysQueryService.findMemberSurvey(member.getId(), surveyId);

        // then
        assertThat(response.questions()).isEmpty();
    }

    @Test
    void 회원과_결과가_30개_이상_같고_성별이_다른_회원을_조회한다() {
        // given
        Member member = memberRepository.save(회원_생성_닉네임_전화번호_성별("회원 1", "000-000-0001", Gender.MALE));
        Member notVisibleMaleMember = memberRepository.save(회원_생성_닉네임_전화번호_성별("회원 2", "000-000-0002", Gender.MALE));
        Member notVisibleFemaleMember = memberRepository.save(회원_생성_닉네임_전화번호_성별("회원 3", "000-000-0003", Gender.FEMALE));
        Member visibleFemaleMember = memberRepository.save(회원_생성_닉네임_전화번호_성별("회원 4", "000-000-0004", Gender.FEMALE));

        Survey survey = surveyRepository.save(연애고사_필수_과목_질문_30개씩());
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_과목_답안(member.getId(), survey.getId(), 1));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_과목_답안(notVisibleMaleMember.getId(), survey.getId(), 1));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_과목_답안(notVisibleFemaleMember.getId(), survey.getId(), 2));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_과목_답안(visibleFemaleMember.getId(), survey.getId(), 1));

        // when
        List<SurveySoulmateResponse> soulmates = memberSurveysQueryService.findSoulmates(member.getId());

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(soulmates.size()).isEqualTo(1);
            softly.assertThat(soulmates.get(0).id()).isEqualTo(visibleFemaleMember.getId());
            softly.assertThat(soulmates.get(0).id()).isNotEqualTo(notVisibleMaleMember.getId());
            softly.assertThat(soulmates.get(0).id()).isNotEqualTo(notVisibleFemaleMember.getId());
        });
    }

    @Nested
    class 연애고사_조회_예외 {

        @Test
        void 아예_연애고사를_작성하지_않았으면_내역_조회_시_예외가_발생한다() {
            // given
            Member member = memberRepository.save(회원_생성());
            Survey survey = surveyRepository.save(연애고사_필수_과목_질문_두개씩());

            // when & then
            assertThatThrownBy(() -> memberSurveysQueryService.findMemberSurvey(member.getId(), survey.getId()))
                            .isInstanceOf(MemberSurveysNotFoundException.class);
        }

        @Test
        void 아예_연애고사를_작성하지_않았으면_매칭_시_예외가_발생한다() {
            // given
            Member male = memberRepository.save(회원_생성_닉네임_전화번호_성별("남성", "000-000-0000", Gender.MALE));
            Member female = memberRepository.save(회원_생성_닉네임_전화번호_성별("여성", "000-000-0001", Gender.FEMALE));

            surveyRepository.save(연애고사_필수_과목_질문_30개씩());
            memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id(female.getId()));

            // when & then
            assertThatThrownBy(() -> memberSurveysQueryService.findSoulmates(male.getId()))
                    .isInstanceOf(MemberSurveysNotFoundException.class);
        }
    }
}
