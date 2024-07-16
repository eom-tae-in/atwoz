package com.atwoz.member.ui.selfintro;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.application.selfintro.dto.SelfIntroCreateRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroUpdateRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntrosResponse;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.selfintro.SelfIntro;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.member.MemberFixture.일반_유저_생성;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters")
class SelfIntroControllerAcceptanceTestFixture extends IntegrationHelper {

    @Autowired
    private SelfIntroRepository selfIntroRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberTokenProvider memberTokenProvider;

    protected Member 회원_저장() {
        return memberRepository.save(일반_유저_생성());
    }

    protected SelfIntro 셀프_소개글_저장(final SelfIntro selfIntro) {
        return selfIntroRepository.save(selfIntro);
    }

    protected String 토큰_생성(final Member member) {
        return memberTokenProvider.createAccessToken(member.getId());
    }

    protected ExtractableResponse<Response> 셀프_소개글_저장_요청(final SelfIntroCreateRequest selfIntroCreateRequest,
                                                         final String url,
                                                         final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(selfIntroCreateRequest)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 셀프_소개글_저장_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 셀프_소개글_페이징_조회_요청(final String url,
                                                             final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 셀프_소개글_페이징_조회_요청_겅즘(final ExtractableResponse<Response> response) {
        SelfIntrosResponse selfIntrosResponse = response.as(SelfIntrosResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(selfIntrosResponse.selfIntros().size()).isEqualTo(1);
            softly.assertThat(selfIntrosResponse.nowPage()).isEqualTo(0);
            softly.assertThat(selfIntrosResponse.nextPage()).isEqualTo(-1);
            softly.assertThat(selfIntrosResponse.totalPages()).isEqualTo(1);
        });
    }

    protected ExtractableResponse<Response> 필터_적용한_셀프_소개글_페이징_조회_요청(final String url,
                                                                    final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
                .param("minAge", "20")
                .param("maxAge", "30")
                .param("isOnlyOppositeGender", "false")
                .param("cityRequests", "서울시,경기도")
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 필터_적용한_셀프_소개글_페이징_조회_요청_검증(final ExtractableResponse<Response> response) {
        SelfIntrosResponse selfIntrosResponse = response.as(SelfIntrosResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(selfIntrosResponse.selfIntros().size()).isEqualTo(1);
            softly.assertThat(selfIntrosResponse.nowPage()).isEqualTo(0);
            softly.assertThat(selfIntrosResponse.nextPage()).isEqualTo(-1);
            softly.assertThat(selfIntrosResponse.totalPages()).isEqualTo(1);
        });
    }

    protected ExtractableResponse<Response> 셀프_소개글_수정_요청(final SelfIntroUpdateRequest selfIntroUpdateRequest,
                                                         final String url,
                                                         final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(selfIntroUpdateRequest)
                .when()
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected void 셀프_소개글_수정_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 셀프_소개글_삭제_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected void 셀프_소개글_삭제_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }
}
