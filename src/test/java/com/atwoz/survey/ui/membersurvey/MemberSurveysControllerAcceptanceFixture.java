package com.atwoz.survey.ui.membersurvey;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import com.atwoz.survey.ui.membersurvey.dto.SurveySoulmateResponses;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_성별;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
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

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

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

    protected Member 회원_생성(final String nickname, final String phoneNumber) {
        return memberRepository.save(회원_생성_닉네임_전화번호_취미목록_스타일목록(
                nickname,
                phoneNumber,
                hobbies,
                styles
        ));
    }

    protected Member 회원_생성_성별(final String nickname, final String phoneNumber, final Gender gender) {
        return memberRepository.save(회원_생성_닉네임_전화번호_성별(nickname, phoneNumber, gender));
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

    protected void 연애고사_매칭_검증(final ExtractableResponse<Response> response, final Long notOneId, final Long notTwoId, final Long findId) {
        SurveySoulmateResponses responses = response.as(SurveySoulmateResponses.class);
        List<SurveySoulmateResponse> soulmates = responses.soulmates();
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(soulmates.size()).isEqualTo(1);
            SurveySoulmateResponse answer = soulmates.get(0);
            softly.assertThat(answer.id()).isNotEqualTo(notOneId);
            softly.assertThat(answer.id()).isNotEqualTo(notTwoId);
            softly.assertThat(answer.id()).isEqualTo(findId);
        });
    }
}
