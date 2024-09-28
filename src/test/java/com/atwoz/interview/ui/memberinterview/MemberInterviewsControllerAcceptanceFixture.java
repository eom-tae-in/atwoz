package com.atwoz.interview.ui.memberinterview;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewUpdateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewUpdateResponse;
import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewsResponse;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_타입_질문;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberInterviewsControllerAcceptanceFixture extends IntegrationHelper {

    private static final String UPDATE_ANSWER = "수정 답변";

    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;

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

    private List<Hobby> hobbies;

    private List<Style> styles;

    @BeforeEach
    void init() {
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "code1"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
    }

    protected Member 회원_생성() {
        return memberRepository.save(회원_생성_취미목록_스타일목록(hobbies, styles));
    }

    protected String 토큰_생성(final Member member) {
        return memberJwtTokenProvider.createAccessToken(member.getId());
    }

    protected void 인터뷰_생성() {
        interviewRepository.save(Interview.createWith("내가 생각하는 내 장점과 단점은 이거다!", "나"));
    }

    protected ExtractableResponse<Response> 인터뷰_응시_요청(
            final String url,
            final String token,
            final Long interviewId,
            final MemberInterviewSubmitRequest request
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when()
                .post(url + "/" + interviewId)
                .then()
                .extract();
    }

    protected void 인터뷰_응시_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected ExtractableResponse<Response> 인터뷰_수정_요청(
            final String url,
            final String token,
            final Long interviewId,
            final MemberInterviewUpdateRequest request
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when()
                .patch(url + "/" + interviewId)
                .then()
                .extract();
    }

    protected void 인터뷰_수정_검증(final ExtractableResponse<Response> response) {
        MemberInterviewUpdateResponse result = response.as(MemberInterviewUpdateResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.id()).isEqualTo(1L);
            softly.assertThat(result.answer()).isEqualTo(UPDATE_ANSWER);
        });
    }

    protected void 인터뷰_응시(final Long memberId) {
        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
        Optional<Interview> interview = interviewRepository.findById(1L);
        memberInterviews.submitInterview(interview.get(), "답변");
        memberInterviewsRepository.save(memberInterviews);
    }

    protected ExtractableResponse<Response> 인터뷰_조회_요청(final String url, final Long interviewId, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when()
                .get(url + "/" + interviewId)
                .then()
                .extract();
    }

    protected void 인터뷰_조회_검증(final ExtractableResponse<Response> response) {
        MemberInterviewDetailResponse result = response.as(MemberInterviewDetailResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.id()).isEqualTo(1L);
            softly.assertThat(result.question()).isEqualTo("내가 생각하는 내 장점과 단점은 이거다!");
            softly.assertThat(result.answer()).isEqualTo("답변");
        });
    }

    protected void 인터뷰_목록_생성() {
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 내 장점과 단점은 이거다!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "나의 평일/주말 생활 패턴"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "일상에서 느끼는 나의 소소한 행복"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "남사친/여사친에 대한 나의 생각"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "나의 인간관계 스타일"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "호감을 느끼는 사람의 유형/타입"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "추구하는 만남 횟수와 연락 빈도"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "내가 연인을 사랑하는 방식"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인에게 바라는 점"));
    }

    protected void 인터뷰_목록_응시(final Long memberId) {
        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
        Optional<Interview> interviewOne = interviewRepository.findById(1L);
        Optional<Interview> interviewTwo = interviewRepository.findById(2L);
        memberInterviews.submitInterview(interviewOne.get(), "답 1");
        memberInterviews.submitInterview(interviewTwo.get(), "답 2");

        memberInterviewsRepository.save(memberInterviews);
    }

    protected ExtractableResponse<Response> 인터뷰_목록_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when()
                .get(url)
                .then()
                .extract();
    }

    protected void 인터뷰_타입_조회_검증(final ExtractableResponse<Response> response) {
        MemberInterviewsResponse result = response.as(MemberInterviewsResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.interviews()).extracting(MemberInterviewSimpleResponse::question)
                    .containsExactly(
                            "내가 생각하는 내 장점과 단점은 이거다!",
                            "나의 평일/주말 생활 패턴",
                            "일상에서 느끼는 나의 소소한 행복"
                    );
            softly.assertThat(result.interviews()).extracting(MemberInterviewSimpleResponse::isSubmitted)
                    .containsExactly(
                            true,
                            true,
                            false
                    );
        });
    }
}
