package com.atwoz.member.ui.member.profile.hobby;

import com.atwoz.admin.application.auth.AdminAccessTokenProvider;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyCreateRequest;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyPagingResponses;
import com.atwoz.member.application.member.profile.hobby.dto.HobbyUpdateRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성_이름_코드;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyControllerAcceptanceTestFixture extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdminAccessTokenProvider adminAccessTokenProvider;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

    private List<Hobby> hobbies;

    private List<Style> styles;

    @BeforeEach
    void init() {
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "code1"));
    }

    protected Member 회원_생성() {
        return memberRepository.save(회원_생성_취미목록_스타일목록(hobbies, styles));
    }

    protected String 토큰_생성(final Member member) {
        return adminAccessTokenProvider.createAccessToken(member.getId());
    }

    protected Hobby 새로운_취미_생성() {
        return hobbyRepository.save(취미_생성_이름_코드("hobby2", "code2"));
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
                .when()
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
