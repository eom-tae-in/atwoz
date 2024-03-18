package com.atwoz.mission.ui.membermission;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.auth.JwtTokenProvider;
import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionPagingResponse;
import com.atwoz.mission.ui.membermission.dto.RewardResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import static com.atwoz.member.fixture.domain.member.MemberFixture.일반_유저_생성;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개_id없음;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MemberMissionsRepository memberMissionsRepository;

    protected Member 회원_생성() {
        return memberRepository.save(일반_유저_생성());
    }

    protected String 토큰_생성(final String email) {
        return jwtTokenProvider.createTokenWith(email);
    }

    protected Mission 미션_생성() {
        return missionRepository.save(미션_생성_리워드_100_데일리_공개_id없음());
    }

    protected ExtractableResponse 회원_미션을_페이징_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_미션_페이징_조회_결과_검증(final ExtractableResponse response) {
        MemberMissionPagingResponse result = response.as(MemberMissionPagingResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.memberMissions()).isNotEmpty();
            softly.assertThat(result.nextPage()).isNotNull();
        });
    }

    protected ExtractableResponse 회원_미션을_클리어_여부로_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_미션_status_조회_결과_검증(final ExtractableResponse response) {
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(Optional.ofNullable(response.path("missionId"))).isPresent();
            softly.assertThat(Optional.ofNullable(response.path("doesGetReward"))).isPresent();
            softly.assertThat(Optional.ofNullable(response.path("isStatusClear"))).isPresent();
            softly.assertThat(Optional.ofNullable(response.path("reward"))).isPresent();
        });
    }

    protected  ExtractableResponse 회원_미션_목록의_완료된_미션의_보상_총합을_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 회원의_완료된_미션_보상_총합_조회_결과_검증(final ExtractableResponse response) {
        RewardResponse result = response.as(RewardResponse.class);
        assertSoftly(softly -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.reward()).isEqualTo(미션_생성_리워드_100_데일리_공개().getReward());
        });
    }

    protected void 회원의_완료된_미션_보상_총합_조회_결과_예외_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    protected void 회원_완료_미션_등록() {
        MemberMission memberMission = MemberMission.createDefault(미션_생성());
        MemberMissions memberMissions = MemberMissions.createWithMemberId(1L);

        memberMissions.addMission(memberMission);
        memberMissions.clearMission(memberMission.getMission().getId());

        memberMissionsRepository.save(memberMissions);
    }

    protected void 회원_미완료_미션_등록() {
        MemberMission memberMission = MemberMission.createDefault(미션_생성());
        MemberMissions memberMissions = MemberMissions.createWithMemberId(1L);

        memberMissions.addMission(memberMission);

        memberMissionsRepository.save(memberMissions);
    }

    protected ExtractableResponse 회원_미션을_등록한다(final String token, final Long missionId, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .param("missionId", missionId)
                .post(url + missionId)
                .then().log().all()
                .extract();
    }

    protected void 회원_미션_등록_결과_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected void 회원_미션_등록_결과_예외_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    protected ExtractableResponse 회원의_완료된_특정_미션_보상을_조회한다(final String token, final Long missionId, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .param("missionId", missionId)
                .post(url + missionId + "/reward")
                .then().log().all()
                .extract();
    }

    protected void 회원의_완료된_특정_미션_보상_조회_결과_검증(final ExtractableResponse response) {
        RewardResponse result = response.as(RewardResponse.class);
        assertSoftly(softly -> {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.reward()).isEqualTo(미션_생성_리워드_100_데일리_공개().getReward());
        });
    }

    protected void 회원의_완료된_특정_미션_보상_조회_회원_미션_없음_예외_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    protected void 회원의_완료된_특정_미션_보상_조회_미완료_예외_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    protected void 회원의_완료된_특정_미션_보상_조회_미션_없음_예외_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    protected ExtractableResponse 회원의_미션을_클리어한다(final String token, final Long missionId, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .param("missionId", missionId)
                .patch(url + missionId)
                .then().log().all()
                .extract();
    }

    protected void 회원_미션_클리어_결과_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected void 회원_미션_클리어_결과_미션_없음_예외_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
