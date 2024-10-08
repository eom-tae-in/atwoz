package com.atwoz.hobby.ui;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.hobby.application.dto.HobbyCreateRequest;
import com.atwoz.hobby.application.dto.HobbyPagingResponses;
import com.atwoz.hobby.application.dto.HobbyUpdateRequest;
import com.atwoz.hobby.domain.Hobby;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.hobby.fixture.취미_픽스처;
import com.atwoz.hobby.infrasturcture.dto.HobbyPagingResponse;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyControllerAcceptanceTestFixture extends IntegrationHelper {

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberTokenProvider memberTokenProvider;


    //TODO: 이거 나중에 수정해야함
    protected Member 회원_생성() {
        return memberRepository.save(Member.createWithOAuth("01012345678"));
    }

    protected String 토큰_생성(final Member member) {
        return memberTokenProvider.createAccessToken(member.getId());
    }

    protected Hobby 취미_생성() {
        return hobbyRepository.save(취미_픽스처.취미_생성());
    }

    protected ExtractableResponse<Response> 취미_저장_요청(
            final HobbyCreateRequest hobbyCreateRequest,
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(hobbyCreateRequest)
                .when().log().all()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 취미_저장_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 취미_단건_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 취미_단건_조회_요청_검증(final ExtractableResponse<Response> response) {
        HobbyPagingResponse hobbyPagingResponse = response.as(HobbyPagingResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(hobbyPagingResponse.name()).isEqualTo("hobby1");
            softly.assertThat(hobbyPagingResponse.code()).isEqualTo("code1");
        });
    }

    protected ExtractableResponse<Response> 취미_페이징_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 취미_페이징_조회_요청_검증(final ExtractableResponse<Response> response) {
        HobbyPagingResponses hobbyPagingResponses = response.as(HobbyPagingResponses.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(hobbyPagingResponses.nowPage()).isEqualTo(0);
            softly.assertThat(hobbyPagingResponses.nextPage()).isEqualTo(-1);
            softly.assertThat(hobbyPagingResponses.totalPages()).isEqualTo(1);
            softly.assertThat(hobbyPagingResponses.totalElements()).isEqualTo(1);
        });
    }

    protected ExtractableResponse<Response> 취미_수정_요청(
            final HobbyUpdateRequest hobbyUpdateRequest,
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(hobbyUpdateRequest)
                .when().log().all()
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected void 취미_수정_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 취미_삭제_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected void 취미_삭제_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }
}
