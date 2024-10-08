package com.atwoz.alert.ui;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertPagingResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import com.atwoz.global.fixture.PhoneNumberGenerator;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.alert.fixture.AlertFixture.알림_생성_제목_날짜_회원id_주입;
import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성_전화번호;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class AlertControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private MemberJwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AlertRepository alertRepository;

    private PhoneNumberGenerator phoneNumberGenerator;

    @BeforeEach
    void init() {
        phoneNumberGenerator = new PhoneNumberGenerator();
    }

    protected Member 회원_생성() {
        return memberRepository.save(회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber()));
    }

    protected String 토큰_생성(final Member member) {
        return jwtTokenProvider.createAccessToken(member.getId());
    }

    protected void 알림_목록_생성(final Long memberId) {
        for (int day = 1; day <= 10; day++) {
            Alert alert = 알림_생성_제목_날짜_회원id_주입("알림 제목 " + day, day, memberId);
            alertRepository.save(alert);
        }
    }

    protected ExtractableResponse 알림_목록을_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 알림_목록_조회_결과_검증(final ExtractableResponse response) {
        AlertPagingResponse result = response.as(AlertPagingResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.alerts().size()).isEqualTo(3);
            softly.assertThat(result.nextPage()).isEqualTo(1);
        });
    }

    protected Alert 알림_생성(final Long memberId) {
        Alert alert = 알림_생성_제목_날짜_회원id_주입("알림 제목", 0, memberId);
        return alertRepository.save(alert);
    }

    protected ExtractableResponse 알림을_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 알림_조회_검증(final ExtractableResponse response) {
        AlertSearchResponse result = response.as(AlertSearchResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.alert()).isNotNull();
            softly.assertThat(result.isRead()).isTrue();
        });
    }
}
