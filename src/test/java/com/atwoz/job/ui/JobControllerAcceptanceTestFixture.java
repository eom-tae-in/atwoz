package com.atwoz.job.ui;

import com.atwoz.admin.application.auth.AdminAccessTokenProvider;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.job.application.dto.JobCreateRequest;
import com.atwoz.job.application.dto.JobPagingResponses;
import com.atwoz.job.application.dto.JobUpdateRequest;
import com.atwoz.job.domain.Job;
import com.atwoz.job.domain.JobRepository;
import com.atwoz.job.fixture.직업_픽스처;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class JobControllerAcceptanceTestFixture extends IntegrationHelper {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdminAccessTokenProvider adminAccessTokenProvider;

    protected Member 회원_생성() {
        return memberRepository.save(Member.createWithOAuth("01011111111"));
    }

    protected String 토큰_생성(final Member member) {
        return adminAccessTokenProvider.createAccessToken(member.getId());
    }

    protected Job 직업_생성() {
        return jobRepository.save(직업_픽스처.직업_생성());
    }

    protected ExtractableResponse<Response> 직업_저장_요청(
            final JobCreateRequest jobCreateRequest,
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jobCreateRequest)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 직업_저장_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 직업_단건_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 직업_단건_조회_요청_검증(final ExtractableResponse<Response> response) {
        JobPagingResponse jobPagingResponse = response.as(JobPagingResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(jobPagingResponse.name()).isEqualTo("job1");
            softly.assertThat(jobPagingResponse.code()).isEqualTo("code1");
        });
    }

    protected ExtractableResponse<Response> 직업_페이징_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 직업_페이징_조회_요청_검증(final ExtractableResponse<Response> response) {
        JobPagingResponses jobPagingResponses = response.as(JobPagingResponses.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(jobPagingResponses.nowPage()).isEqualTo(0);
            softly.assertThat(jobPagingResponses.nextPage()).isEqualTo(-1);
            softly.assertThat(jobPagingResponses.totalPages()).isEqualTo(1);
            softly.assertThat(jobPagingResponses.totalElements()).isEqualTo(1);
        });
    }

    protected ExtractableResponse<Response> 직업_수정_요청(
            final JobUpdateRequest jobUpdateRequest,
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(jobUpdateRequest)
                .when().log().all()
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected void 직업_수정_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 직업_삭제_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected void 직업_삭제_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }
}
