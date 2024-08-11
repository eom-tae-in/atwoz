package com.atwoz.report.ui;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.fixture.member.domain.MemberFixture;
import com.atwoz.report.application.dto.ReportCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class ReportControllerAcceptanceFixture extends IntegrationHelper {

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
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "code1"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
    }

    protected Member 회원_생성() {
        return memberRepository.save(MemberFixture.회원_생성_취미목록_스타일목록(hobbies, styles));
    }

    protected String 토큰_생성(final Member member) {
        return memberTokenProvider.createAccessToken(member.getId());
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
