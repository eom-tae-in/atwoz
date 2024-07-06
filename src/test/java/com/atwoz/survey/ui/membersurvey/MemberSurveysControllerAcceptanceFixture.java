package com.atwoz.survey.ui.membersurvey;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.ui.membersurvey.dto.MatchMemberSearchResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_선택_과목_질문_두개씩;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_30개씩;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberSurveysControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    protected Member 회원_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected Member 회원_생성(final String nickname, final String phoneNumber) {
        return memberRepository.save(일반_유저_생성(nickname, phoneNumber));
    }

    protected String 토큰_생성(final Member member) {
        return memberJwtTokenProvider.createAccessToken(member.getId());
    }

    protected void 연애고사_필수_과목_질문_두개씩_생성() {
        surveyRepository.save(연애고사_선택_과목_질문_두개씩());
    }

    protected void 연애고사_필수_과목_질문_30개씩_생성() {
        surveyRepository.save(연애고사_필수_과목_질문_30개씩());
    }

    protected ExtractableResponse<Response> 연애고사_응시_요청(final String url, final String token, final List<SurveySubmitRequest> requests) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(requests)
                .when()
                .post(url)
                .then()
                .extract();
    }

    protected void 연애고사_응시_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected ExtractableResponse<Response> 연애고사_응시_조회(final String url, final String token, final Long surveyId) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when()
                .get(url + "/" + surveyId)
                .then()
                .extract();
    }

    protected void 연애고사_조회_검증(final ExtractableResponse<Response> response) {
        MemberSurveyResponse surveyResponse = response.as(MemberSurveyResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(surveyResponse.surveyId()).isEqualTo(1L);
            softly.assertThat(surveyResponse.questions().size()).isEqualTo(2);
        });
    }

    protected ExtractableResponse 연애고사_매칭(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when()
                .get(url)
                .then()
                .extract();
    }

    protected void 연애고사_매칭_검증(final ExtractableResponse<Response> response, final Long memberOneId, final Long memberTwoId) {
        MatchMemberSearchResponse match = response.as(MatchMemberSearchResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(match.members()).contains(memberOneId);
            softly.assertThat(match.members()).doesNotContain(memberTwoId);
        });
    }
}
