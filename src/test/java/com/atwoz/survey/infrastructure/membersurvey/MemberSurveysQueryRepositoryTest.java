package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_성별_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_30개_응시_저장_id;
import static com.atwoz.survey.fixture.MemberSurveysFixture.회원_연애고사_필수_과목_질문_30개_응시_저장_id_번호;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_30개씩;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberSurveysQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private MemberSurveysRepository memberSurveysRepository;

    @Autowired
    private MemberSurveysQueryRepository memberSurveysQueryRepository;

    private List<Hobby> hobbies;

    private List<Style> styles;

    @BeforeEach
    void setup() {
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "hobby2"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
    }

    @Test
    void 연애고사_매칭_결과가_30개_이상_같고_성별이_다른_회원을_조회한다() {
        // given
        surveyRepository.save(연애고사_필수_과목_질문_30개씩());

        Member loginMember = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("LoginMember", "000-0000-0000", Gender.MALE, hobbies, styles));
        Member notVisibleMaleMember = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("maleMember", "000-0000-0001", Gender.MALE, hobbies, styles));
        Member notVisibleFemaleMember = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("femaleMember1", "000-0000-0002", Gender.FEMALE, hobbies, styles));
        Member visibleFemaleMember = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("femaleMember2", "000-0000-0003", Gender.FEMALE, hobbies, styles));

        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_번호(loginMember.getId(), 1));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_번호(notVisibleMaleMember.getId(), 1));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_번호(notVisibleFemaleMember.getId(), 2));
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id_번호(visibleFemaleMember.getId(), 1));

        // when
        List<SurveySoulmateResponse> soulmates = memberSurveysQueryRepository.findSoulmates(loginMember.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(soulmates.size()).isEqualTo(1);
            SurveySoulmateResponse response = soulmates.get(0);
            softly.assertThat(response.id()).isNotEqualTo(notVisibleMaleMember.getId());
            softly.assertThat(response.id()).isNotEqualTo(notVisibleFemaleMember.getId());
            softly.assertThat(response.id()).isEqualTo(visibleFemaleMember.getId());
        });
    }

    @Test
    void 회원의_연애고사_결과를_과목_id로_조회한다() {
        // given
        Member loginMember = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("LoginMember", "000-0000-0000", Gender.MALE, hobbies, styles));
        Survey survey = 연애고사_필수_과목_질문_30개씩();
        surveyRepository.save(survey);
        memberSurveysRepository.save(회원_연애고사_필수_과목_질문_30개_응시_저장_id(loginMember.getId()));

        // when
        Optional<MemberSurveyResponse> memberSurvey = memberSurveysQueryRepository.findMemberSurvey(loginMember.getId(), survey.getId());

        // then
        assertThat(memberSurvey.isPresent()).isTrue();
    }
}
