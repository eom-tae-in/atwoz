package com.atwoz.interview.application.memberinterview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import com.atwoz.interview.infrastructure.interview.InterviewFakeRepository;
import com.atwoz.interview.infrastructure.memberinterview.MemberInterviewsFakeRepository;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_타입_질문;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInterviewsQueryServiceTest {

    private MemberInterviewsQueryService memberInterviewsQueryService;
    private MemberInterviewsRepository memberInterviewsRepository;
    private InterviewRepository interviewRepository;

    @BeforeEach
    void init() {
        interviewRepository = new InterviewFakeRepository();
        memberInterviewsRepository = new MemberInterviewsFakeRepository(interviewRepository);
        memberInterviewsQueryService = new MemberInterviewsQueryService(memberInterviewsRepository);
    }

    @Nested
    class 회원_인터뷰_단일_조회 {

        @Test
        void 회원의_특정_인터뷰_내역을_조회한다() {
            // given
            Long interviewId = 1L;
            Long memberId = 1L;
            String question = "질문";
            String answer = "답변";
            Interview interview = interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, question));
            MemberInterviews memberInterviews = memberInterviewsRepository.save(MemberInterviews.createWithMemberId(memberId));
            memberInterviews.submitInterview(interview, answer);

            // when
            MemberInterviewDetailResponse response = memberInterviewsQueryService.findMemberInterviewAnswer(interviewId, memberId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(response.id()).isEqualTo(interviewId);
                softly.assertThat(response.question()).isEqualTo(question);
                softly.assertThat(response.answer()).isEqualTo(answer);
            });
        }

        @Test
        void 응시하지_않은_인터뷰면_빈_문자열을_답변으로_반환한다() {
            // given
            Long interviewId = 1L;
            Long memberId = 1L;
            String question = "질문";
            String answer = "";
            interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, question));

            // when
            MemberInterviewDetailResponse response = memberInterviewsQueryService.findMemberInterviewAnswer(interviewId, memberId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(response.id()).isEqualTo(interviewId);
                softly.assertThat(response.question()).isEqualTo(question);
                softly.assertThat(response.answer()).isEqualTo(answer);
            });
        }
    }

    @Nested
    class 회원_인터뷰_목록_조회 {

        @Test
        void 회원의_인터뷰_내역을_타입으로_조회한다() {
            // given
            Long memberId = 1L;
            String type = "나";

            Interview interviewOne = interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 내 장점과 단점은 이거다!"));
            Interview interviewTwo = interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "나의 평일/주말 생활 패턴"));
            saveOtherInterviews();

            MemberInterviews memberInterviews = memberInterviewsRepository.save(MemberInterviews.createWithMemberId(memberId));
            memberInterviews.submitInterview(interviewOne, "답변1");
            memberInterviews.submitInterview(interviewTwo, "답변2");

            // when
            List<MemberInterviewSimpleResponse> interviews = memberInterviewsQueryService.findMemberInterviewsByType(memberId, type);

            // then
            assertSoftly(softly -> {
                softly.assertThat(interviews.size()).isEqualTo(3);
                softly.assertThat(interviews).extracting(MemberInterviewSimpleResponse::question)
                        .containsExactly(
                                "내가 생각하는 내 장점과 단점은 이거다!",
                                "나의 평일/주말 생활 패턴",
                                "일상에서 느끼는 나의 소소한 행복"
                        );
                softly.assertThat(interviews).extracting(MemberInterviewSimpleResponse::isSubmitted)
                        .containsExactly(
                                true,
                                true,
                                false
                        );
            });
        }

        @Test
        void 없는_인터뷰_타입으로_조회하면_예외가_발생한다() {
            // given
            Long memberId = 1L;
            String type = "abc";

            Interview interviewOne = interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 내 장점과 단점은 이거다!"));
            Interview interviewTwo = interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "나의 평일/주말 생활 패턴"));
            saveOtherInterviews();

            MemberInterviews memberInterviews = memberInterviewsRepository.save(MemberInterviews.createWithMemberId(memberId));
            memberInterviews.submitInterview(interviewOne, "답변1");
            memberInterviews.submitInterview(interviewTwo, "답변2");
            
            // when & then
            assertThatThrownBy(() -> memberInterviewsQueryService.findMemberInterviewsByType(memberId, type))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }
    }

    private void saveOtherInterviews() {
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "일상에서 느끼는 나의 소소한 행복"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "남사친/여사친에 대한 나의 생각"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "나의 인간관계 스타일"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "호감을 느끼는 사람의 유형/타입"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "추구하는 만남 횟수와 연락 빈도"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "내가 연인을 사랑하는 방식"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인에게 바라는 점"));
    }
}
