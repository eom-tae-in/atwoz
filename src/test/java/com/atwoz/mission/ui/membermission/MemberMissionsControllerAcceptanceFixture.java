package com.atwoz.mission.ui.membermission;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.fixture.member.domain.MemberFixture;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionPagingResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함_데일리_미션_시간있음;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개_id없음;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_200_챌린지_id없음;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private MemberJwtTokenProvider memberJwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MemberMissionsRepository memberMissionsRepository;

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
        return memberJwtTokenProvider.createAccessToken(member.getId());
    }

    protected Mission 데일리_미션_생성() {
        return missionRepository.save(미션_생성_리워드_100_데일리_공개_id없음());
    }

    protected Mission 챌린지_미션_생성() {
        return missionRepository.save(미션_생성_리워드_200_챌린지_id없음());
    }

    protected ExtractableResponse 회원_미션을_페이징_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(ContentType.JSON)
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

    protected void 회원_완료_미션_등록_보상_미수령() {
        MemberMissions memberMissions = MemberMissions.createWithMemberId(1L);

        Gender memberGender = Gender.MALE;
        Mission dailyMission = 데일리_미션_생성();
        Mission challengeMission = 챌린지_미션_생성();

        for (int i = 0; i < 2; i++) {
            MemberMission dailyMemberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리_미션_시간있음(dailyMission);
            memberMissions.addClearedMission(memberGender, dailyMemberMission);
        }
        memberMissions.addClearedMission(memberGender, MemberMission.createDefault(challengeMission));

        memberMissionsRepository.save(memberMissions);
    }
}
