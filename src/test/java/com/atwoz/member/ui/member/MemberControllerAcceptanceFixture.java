package com.atwoz.member.ui.member;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.application.member.MemberService;
import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberNicknameRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.fixture.MemberRequestFixture;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_초기화_요청서_요청;
import static com.atwoz.member.fixture.domain.member.MemberFixture.일반_유저_생성;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected TokenProvider tokenProvider;

    protected Member 회원_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected Member PASS_인증만_완료된_회원_생성() {
        return memberRepository.save(Member.createWithPass("남성", "01011111111"));
    }

    protected String 토큰_생성(final Member member) {
        String phoneNumber = member.getPhoneNumber();
        return tokenProvider.createTokenWith(phoneNumber);
    }

    protected MemberNicknameRequest 회원_닉네임_중복_확인서를_요청한다() {
        return new MemberNicknameRequest("uniqueNickname");
    }

    protected ExtractableResponse<Response> 회원_닉네임_중복을_확인한다(final String uri, final String token,
                                                            final MemberNicknameRequest nicknameRequest) {

        return RestAssured.given().log().all()
                .body(nicknameRequest)
                .contentType(JSON)
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .get(uri)
                .then()
                .extract();
    }

    protected void 회원_닉네임_중복_확인_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected MemberInitializeRequest 회원_초기_정보를_요청한다() {
        return 회원_정보_초기화_요청서_요청();
    }

    protected ExtractableResponse<Response> 회원_정보_초기화_요청(final String uri, final String token,
                                                         final MemberInitializeRequest memberInitializeRequest) {

        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(memberInitializeRequest)
                .when()
                .post(uri)
                .then()
                .extract();
    }

    protected void 회원_정보_초기화_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected MemberUpdateRequest 회원_수정_정보를_요청한다() {
        return MemberRequestFixture.회원_정보_수정_요청서_요청();
    }

    protected ExtractableResponse<Response> 회원_정보_수정_요청(final String uri, final String token,
                                                        final MemberUpdateRequest memberUpdateRequest) {

        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(memberUpdateRequest)
                .when()
                .patch(uri)
                .then()
                .extract();
    }

    protected void 회원_정보_수정_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected ExtractableResponse<Response> 회원_탈퇴_요청(final String uri, final String token) {

        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when()
                .delete(uri)
                .then()
                .extract();
    }

    protected void 회원_탈퇴_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
