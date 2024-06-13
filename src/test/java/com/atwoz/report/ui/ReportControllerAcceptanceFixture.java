package com.atwoz.report.ui;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.report.application.dto.ReportCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class ReportControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected TokenProvider tokenProvider;

    protected Member 회원_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected String 토큰_생성(final Member member) {
        return tokenProvider.createTokenWithId(member.getId());
    }

    protected ReportCreateRequest 신고_요청서_요청() {
        return new ReportCreateRequest(2L, FAKE_PROFILE.getCode(), "사진 도용했어요");
    }

    protected ExtractableResponse<Response> 신고_생성_요청(final String uri, final String token,
                                                     final ReportCreateRequest reportCreateRequest) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(reportCreateRequest)
                .when()
                .post(uri)
                .then()
                .extract();
    }

    protected void 신고_생성_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
