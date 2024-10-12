package com.atwoz.interview.ui.interview;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import com.atwoz.interview.ui.interview.dto.InterviewCreateResponse;
import com.atwoz.interview.ui.interview.dto.InterviewUpdateResponse;
import com.atwoz.interview.ui.interview.dto.InterviewsResponse;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.fixture.member.회원_픽스처;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_나_일반_질문;
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_타입_질문;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class InterviewControllerAcceptanceFixture extends IntegrationHelper {

    private static final String INTERVIEW_INIT_QUESTION = "나는 요즘 이런걸 배워보고 싶더라!";
    private static final String INTERVIEW_INIT_TYPE = "나";
    private static final String INTERVIEW_UPDATE_QUESTION = "인터뷰 질문 수정";
    private static final String INTERVIEW_UPDATE_TYPE = "연인";

    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    protected Member 회원_생성() {
        return memberRepository.save(회원_픽스처.회원_생성());
    }

    protected String 토큰_생성(final Member member) {
        return memberJwtTokenProvider.createAccessToken(member.getId());
    }

    protected ExtractableResponse<Response> 인터뷰_생성_요청(
            final String url,
            final String token,
            final InterviewCreateRequest request
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when()
                .post(url)
                .then()
                .extract();
    }

    protected void 인터뷰_생성_검증(final ExtractableResponse<Response> response) {
        InterviewCreateResponse result = response.as(InterviewCreateResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            softly.assertThat(result.question()).isEqualTo(INTERVIEW_INIT_QUESTION);
            softly.assertThat(result.type()).isEqualTo(INTERVIEW_INIT_TYPE);
        });
    }

    protected void 인터뷰_생성() {
        interviewRepository.save(인터뷰_나_일반_질문());
    }

    protected ExtractableResponse<Response> 인터뷰_수정_요청(
            final String url,
            final String token,
            final InterviewUpdateRequest request,
            final Long id
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when()
                .patch(url + "/" + id)
                .then()
                .extract();
    }

    protected void 인터뷰_수정_검증(final ExtractableResponse<Response> response) {
        InterviewUpdateResponse result = response.as(InterviewUpdateResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.question()).isEqualTo(INTERVIEW_UPDATE_QUESTION);
            softly.assertThat(result.type()).isEqualTo(INTERVIEW_UPDATE_TYPE);
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

    protected ExtractableResponse<Response> 인터뷰_조회_요청(
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then()
                .extract();
    }

    protected void 인터뷰_타입_조회_검증(final ExtractableResponse<Response> response) {
        InterviewsResponse result = response.as(InterviewsResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.interviews()).extracting(InterviewResponse::question)
                    .containsExactly(
                            "내가 생각하는 내 장점과 단점은 이거다!",
                            "나의 평일/주말 생활 패턴",
                            "일상에서 느끼는 나의 소소한 행복"
                    );
            softly.assertThat(result.interviews()).extracting(InterviewResponse::type)
                    .containsOnly(InterviewType.ME);
        });
    }
}
