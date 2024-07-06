package com.atwoz.survey.ui.survey;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.ui.survey.dto.SurveyCreateResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    protected Member 회원_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected String 토큰_생성(final Member member) {
        return memberJwtTokenProvider.createAccessToken(member.getId());
    }

    protected ExtractableResponse<Response> 연애고사_과목_생성_요청(final String url, final String token, final SurveyCreateRequest request) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when()
                .post(url)
                .then()
                .extract();
    }

    protected void 연애고사_과목_생성_검증(final ExtractableResponse<Response> response) {
        SurveyCreateResponse result = response.as(SurveyCreateResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            softly.assertThat(result.questions()).isNotEmpty();
        });
    }
}
