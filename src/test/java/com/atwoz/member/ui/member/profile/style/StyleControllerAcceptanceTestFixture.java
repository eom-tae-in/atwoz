package com.atwoz.member.ui.member.profile.style;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.application.member.profile.style.dto.StyleCreateRequest;
import com.atwoz.member.application.member.profile.style.dto.StyleResponses;
import com.atwoz.member.application.member.profile.style.dto.StyleUpdateRequest;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성_이름_코드;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class StyleControllerAcceptanceTestFixture extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberTokenProvider memberTokenProvider;

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
        return memberTokenProvider.createAccessToken(member.getId());
    }

    protected Style 새로운_스타일_생성() {
        return styleRepository.save(스타일_생성_이름_코드("styl2", "code2"));
    }

    protected ExtractableResponse<Response> 스타일_저장_요청(
            final StyleCreateRequest styleCreateRequest,
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(styleCreateRequest)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 스타일_저장_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 스타일_단건_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 스타일_단건_조회_요청_검증(final ExtractableResponse<Response> response) {
        StyleResponse styleResponse = response.as(StyleResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(styleResponse.name()).isEqualTo("style1");
            softly.assertThat(styleResponse.code()).isEqualTo("code1");
        });
    }

    protected ExtractableResponse<Response> 스타일_페이징_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .param("page", "0")
                .param("size", "10")
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 스타일_페이징_조회_요청_검증(final ExtractableResponse<Response> response) {
        StyleResponses styleResponses = response.as(StyleResponses.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
            softly.assertThat(styleResponses.nowPage()).isEqualTo(0);
            softly.assertThat(styleResponses.nextPage()).isEqualTo(-1);
            softly.assertThat(styleResponses.totalPages()).isEqualTo(1);
            softly.assertThat(styleResponses.totalElements()).isEqualTo(1);
        });
    }

    protected ExtractableResponse<Response> 스타일_수정_요청(
            final StyleUpdateRequest styleUpdateRequest,
            final String url,
            final String token
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(styleUpdateRequest)
                .when().log().all()
                .patch(url)
                .then().log().all()
                .extract();
    }

    protected void 스타일_수정_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

    protected ExtractableResponse<Response> 스타일_삭제_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .delete(url)
                .then().log().all()
                .extract();
    }

    protected void 스타일_삭제_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_NO_CONTENT);
    }
}
