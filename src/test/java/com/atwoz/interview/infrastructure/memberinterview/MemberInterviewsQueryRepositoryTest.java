package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_나_질문;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_성별_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInterviewsQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private MemberInterviewsRepository memberInterviewsRepository;

    @Autowired
    private MemberInterviewsQueryRepository memberInterviewsQueryRepository;

    private List<Hobby> hobbies;

    private List<Style> styles;

    @BeforeEach
    void setup() {
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "hobby2"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
    }

    @Test
    void 특정_인터뷰_답변을_조회한다() {
        // given
        Member member = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("Member", "000-000-0000", Gender.MALE, hobbies, styles));
        String question = "내가 생각하는 내 장점과 단점은 이거다!";
        String answer = "답변";

        Interview interviewOne = interviewRepository.save(인터뷰_나_질문(question));
        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(member.getId());
        memberInterviews.submitInterview(interviewOne, answer);
        memberInterviewsRepository.save(memberInterviews);

        // when
        MemberInterviewDetailResponse response = memberInterviewsQueryRepository.findMemberInterviewAnswer(interviewOne.getId(), member.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.id()).isEqualTo(interviewOne.getId());
            softly.assertThat(response.question()).isEqualTo(question);
            softly.assertThat(response.answer()).isEqualTo(answer);
        });
    }

    @Test
    void 아직_답변하지_않은_인터뷰면_빈_문자열을_답변으로_반환한다() {
        // given
        Member member = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("Member", "000-000-0000", Gender.MALE, hobbies, styles));
        String question = "내가 생각하는 내 장점과 단점은 이거다!";
        String answer = "";

        Interview interviewOne = interviewRepository.save(인터뷰_나_질문(question));
        memberInterviewsRepository.save(MemberInterviews.createWithMemberId(member.getId()));

        // when
        MemberInterviewDetailResponse response = memberInterviewsQueryRepository.findMemberInterviewAnswer(interviewOne.getId(), member.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.id()).isEqualTo(interviewOne.getId());
            softly.assertThat(response.question()).isEqualTo(question);
            softly.assertThat(response.answer()).isEqualTo(answer);
        });
    }

    @Test
    void 인터뷰_질문_목록_조회_시_회원의_인터뷰_응시_여부와_함께_조회한다() {
        // given
        Member member = memberRepository.save(회원_생성_닉네임_전화번호_성별_취미목록_스타일목록("Member", "000-000-0000", Gender.MALE, hobbies, styles));
        String type = "나";

        Interview interviewOne = interviewRepository.save(인터뷰_나_질문("내가 생각하는 내 장점과 단점은 이거다!"));
        interviewRepository.save(인터뷰_나_질문("나의 평일과 주말은 이런식으로 보내고 있어!"));
        Interview interviewThree = interviewRepository.save(인터뷰_나_질문("내가 생각하는 나의 반전 매력은 이거야!"));

        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(member.getId());
        memberInterviews.submitInterview(interviewOne, "장점: 성실, 단점: 번아웃");
        memberInterviews.submitInterview(interviewThree, "반전 매력은 놀 때는 제대로 논다는 것!");

        memberInterviewsRepository.save(memberInterviews);

        List<MemberInterviewSimpleResponse> expectedSubmittedResponses = List.of(
                new MemberInterviewSimpleResponse(interviewOne.getId(), interviewOne.getQuestion(), true),
                new MemberInterviewSimpleResponse(interviewThree.getId(), interviewThree.getQuestion(), true));

        // when
        List<MemberInterviewSimpleResponse> interviews = memberInterviewsQueryRepository.findMemberInterviewsByType(member.getId(), type);
        List<MemberInterviewSimpleResponse> submittedInterviews = interviews.stream()
                .filter(MemberInterviewSimpleResponse::isSubmitted)
                .toList();

        // then
        assertSoftly(softly -> {
            softly.assertThat(interviews.size()).isEqualTo(3);
            softly.assertThat(submittedInterviews).isEqualTo(expectedSubmittedResponses);
        });
    }
}
