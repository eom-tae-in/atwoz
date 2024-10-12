package com.atwoz.survey.ui.survey;

import com.atwoz.admin.domain.admin.Admin;
import com.atwoz.admin.infrastructure.auth.AdminJwtTokenProvider;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.ui.survey.dto.SurveyCreateResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private AdminJwtTokenProvider adminJwtTokenProvider;

    protected String 토큰_생성(final Admin admin) {
        return adminJwtTokenProvider.createAccessToken(admin.getId());
    }

    protected ExtractableResponse<Response> 연애고사_과목_생성_요청(
            final String url,
            final String token,
            final SurveyCreateRequest request
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

    protected void 연애고사_과목_생성_검증(final ExtractableResponse<Response> response) {
        SurveyCreateResponse result = response.as(SurveyCreateResponse.class);

        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            softly.assertThat(result.questions()).isNotEmpty();
        });
    }
}
