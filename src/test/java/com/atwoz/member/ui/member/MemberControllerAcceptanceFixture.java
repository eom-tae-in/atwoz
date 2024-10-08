package com.atwoz.member.ui.member;

import com.atwoz.global.fixture.PhoneNumberGenerator;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.application.member.dto.MemberAccountStatusPatchRequest;
import com.atwoz.member.application.member.dto.MemberContactInfoDuplicationCheckResponse;
import com.atwoz.member.application.member.dto.MemberContactInfoPatchRequest;
import com.atwoz.member.application.member.dto.MemberNotificationsPatchRequest;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.ContactType;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_계정_상태_변경_요청_픽스처.회원_계정_상태_변경_요청_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_연락처_정보_변경_요청_픽스처.회원_연락처_정보_변경_요청_생성_연락처;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_푸시_알림_변경_요청_픽스처.회원_푸시_알림_변경_요청_생성_회원푸시알림목록;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_계정_상태_조회_응답_픽스처.회원_계정_상태_조회_응답_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_조회_응답_픽스처.회원_연락처_정보_조회_응답_생성_연락처;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_푸시_알림_조회_응답_픽스처.회원_푸시_알림_조회_응답_생성_회원푸시알림목록;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberControllerAcceptanceFixture extends IntegrationHelper {

    protected final String 회원_푸시_알림_조회_URL = "/api/members/me/notifications";
    protected final String 회원_푸시_알림_변경_URL = "/api/members/me/notifications";
    protected final String 회원_계정_상태_조회_URL = "/api/members/me/statuses/account";
    protected final String 회원_계정_상태_변경_URL = "/api/members/me/statuses/account";
    protected final String 회원_연락처_정보_조회_URL = "/api/members/me/contact-info";
    protected final String 회원_연락처_정보_변경_URL = "/api/members/me/contact-info";
    protected final String 회원_연락처_정보_중복_확인_URL = "/api/members/{contactType}/{contactValue}/duplication";
    protected final String 회원_삭제_URL = "/api/members/me";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberTokenProvider memberTokenProvider;

    private PhoneNumberGenerator phoneNumberGenerator;

    protected Member 회원;
    protected String 토큰;

    @BeforeEach
    void init() {
        회원 = memberRepository.save(Member.createWithOAuth("01011111111"));
        토큰 = memberTokenProvider.createAccessToken(회원.getId());
    }

    protected ExtractableResponse<Response> 회원_푸시_알림_정보_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_푸시_알림_정보_조회_검증(final ExtractableResponse<Response> response) {
        MemberNotificationsPatchRequest request = response.as(MemberNotificationsPatchRequest.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request).usingRecursiveComparison()
                    .isEqualTo(회원_푸시_알림_조회_응답_생성_회원푸시알림목록(회원.getMemberPushNotifications()));
        });
    }

    protected ExtractableResponse<Response> 회원_계정_상태_정보_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_계정_상태_정보_조회_검증(final ExtractableResponse<Response> response) {
        MemberAccountStatusResponse request = response.as(MemberAccountStatusResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request).usingRecursiveComparison()
                    .isEqualTo(회원_계정_상태_조회_응답_생성_회원계정상태(회원.getMemberAccountStatus()));
        });
    }

    protected ExtractableResponse<Response> 회원_연락처_정보_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_연락처_정보_조회_검증(final ExtractableResponse<Response> response) {
        MemberContactInfoResponse request = response.as(MemberContactInfoResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request).usingRecursiveComparison()
                    .isEqualTo(회원_연락처_정보_조회_응답_생성_연락처(회원.getContact()));
        });
    }

    protected String 중복_확인을_할_연락처_타입_생성() {
        return ContactType.PHONE_NUMBER.getType();
    }

    protected String 중복_확인을_할_연락처_정보_생성() {
        return "010111111111";
    }

    protected ExtractableResponse<Response> 회원_연락처_정보_중복_확인_요청(
            final String url,
            final String token,
            final String contactType,
            final String contactValue
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .pathParam("contactType", contactType)
                .pathParam("contactValue", contactValue)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_연락처_정보_중복_확인_검증(final ExtractableResponse<Response> response) {
        MemberContactInfoDuplicationCheckResponse request =
                response.as(MemberContactInfoDuplicationCheckResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request.isDuplicated()).isFalse();
        });
    }

    protected ExtractableResponse<Response> 회원_푸시_알림_변경_요청(
            final String url,
            final String token,
            final MemberNotificationsPatchRequest request
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_푸시_알림_변경_검증(final ExtractableResponse<Response> response) {
        MemberNotificationsPatchRequest request = response.as(MemberNotificationsPatchRequest.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request).usingRecursiveComparison()
                    .isEqualTo(회원_푸시_알림_변경_요청_생성_회원푸시알림목록(회원.getMemberPushNotifications()));
        });
    }

    protected ExtractableResponse<Response> 회원_계정_상태_변경_요청(
            final String url,
            final String token,
            final MemberAccountStatusPatchRequest request
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_계정_상태_변경_검증(final ExtractableResponse<Response> response) {
        MemberAccountStatusPatchRequest request = response.as(MemberAccountStatusPatchRequest.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request).usingRecursiveComparison()
                    .isEqualTo(회원_계정_상태_변경_요청_생성_회원계정상태(회원.getMemberAccountStatus()));
        });
    }

    protected ExtractableResponse<Response> 회원_연락처_정보_변경_요청(
            final String url,
            final String token,
            final MemberContactInfoPatchRequest request
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_연락처_정보_변경_검증(final ExtractableResponse<Response> response) {
        MemberContactInfoPatchRequest request = response.as(MemberContactInfoPatchRequest.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(request).usingRecursiveComparison()
                    .isEqualTo(회원_연락처_정보_변경_요청_생성_연락처(회원.getContact()));
        });
    }

    protected ExtractableResponse<Response> 회원_탈퇴_요청(final String uri, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .delete(uri)
                .then()
                .extract();
    }

    protected void 회원_탈퇴_요청_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
