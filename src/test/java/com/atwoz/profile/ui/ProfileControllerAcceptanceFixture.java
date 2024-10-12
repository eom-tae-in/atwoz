package com.atwoz.profile.ui;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.hobby.domain.HobbyRepository;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.ProfileRepository;
import com.atwoz.profile.fixture.프로필_픽스처;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import com.atwoz.profile.ui.dto.TodayProfilesResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.hobby.fixture.취미_픽스처.취미_생성;
import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성;
import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성_회원등급;
import static com.atwoz.profile.fixture.프로필_응답_픽스처.추천_프로필_조회_응답_픽스처.추천_프로필_조회_응답_생성_프로필;
import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디;
import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_위치;
import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_취미목록;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SuppressWarnings("NonAsciiCharacters")
class ProfileControllerAcceptanceFixture extends IntegrationHelper {

    protected static final String 닉네임_중복_검증_URL = "/api/profiles/{nickname}/duplication";
    protected static final String 오늘의_이성_추천_프로필_조회_URL = "/api/profiles/today";
    protected static final String 인기있는_이성_회원_프로필_조회_URL = "/api/profiles/popularity";
    protected static final String 오늘_방문한_이성_회원_프로필_조회_URL = "/api/profiles/today-visit";
    protected static final String 근처에_있는_이성_회원_프로필_조회_URL = "/api/profiles/nearby";
    protected static final String 최근에_가입한_이성_회원_프로필_조회_URL = "/api/profiles/recency";
    protected static final String 종교가_같은_이성_회원_프로필_조회_URL = "/api/profiles/religion";
    protected static final String 취미가_같은_이성_회원_프로필_조회_URL = "/api/profiles/hobbies";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    @Autowired
    private MemberTokenProvider memberTokenProvider;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    protected String 토큰;

    private Profile 프로필;

    private Member 회원;

    @BeforeEach
    void setup() {
        hobbyRepository.save(취미_생성());
        회원 = memberRepository.save(회원_생성());
        토큰 = memberTokenProvider.createAccessToken(회원.getId());
        프로필 = profileRepository.save(프로필_생성_회원아이디(회원.getId()));
    }

    protected String 새로운_닉네임() {
        return "newNickname";
    }

    protected ExtractableResponse<Response> 닉네임_중복을_확인한다(
            final String url,
            final String token,
            final String nickname
    ) {
        return RestAssured.given().log().all()
                .contentType(JSON)
                .header(AUTHORIZATION, "Bearer " + token)
                .pathParam("nickname", nickname)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 닉네임_중복_확인_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


    protected Profile 다이아_등급_이성_프로필_생성() {
        Member diamondMember = memberRepository.save(회원_생성_회원등급(MemberGrade.DIAMOND));

        return profileRepository.save(프로필_생성_회원아이디(diamondMember.getId()));
    }

    protected Profile 골드_등급_이성_회원_생성() {
        Member goldMember = memberRepository.save(회원_생성_회원등급(MemberGrade.GOLD));

        return profileRepository.save(프로필_생성_회원아이디(goldMember.getId()));
    }


    protected Profile 인기있는_이성_프로필_생성() {
        Member receiver = memberRepository.save(회원_생성());
        MemberLike memberLike = MemberLike.builder()
                .senderId(회원.getId())
                .receiverId(receiver.getId())
                .isRecent(false)
                .build();

        memberLikeRepository.save(memberLike);

        return profileRepository.save(프로필_생성_회원아이디(receiver.getId()));
    }

    protected Profile 오늘_방문한_이성_프로필_생성() {
        Member todayVisitor = memberRepository.save(회원_생성());

        return profileRepository.save(프로필_생성_회원아이디(todayVisitor.getId()));
    }

    protected Profile 근처에_있는_이성_프로필_생성() {
        Member nearbyMember = memberRepository.save(회원_생성());

        return profileRepository.save(프로필_생성_회원아이디_위치(nearbyMember.getId(), 프로필.getLocation()));
    }

    protected Profile 최근에_가입한_이성_프로필_생성() {
        Member newMember = memberRepository.save(회원_생성());

        return profileRepository.save(프로필_생성_회원아이디(newMember.getId()));
    }

    protected Profile 종교가_같은_이성_프로필_생성() {
        Member sameReligionMember = memberRepository.save(회원_생성());

        return profileRepository.save(프로필_픽스처.프로필_생성_회원아이디_종교(sameReligionMember.getId(), 프로필.getReligion()));
    }

    protected Profile 취미가_같은_이성_프로필_생성() {
        Member sameHobbyMember = memberRepository.save(회원_생성());

        return profileRepository.save(프로필_생성_회원아이디_취미목록(sameHobbyMember.getId(), 프로필.getUserHobbies()));
    }

    protected ExtractableResponse<Response> 프로필_조회_요청(
            final String url,
            final String token,
            final ProfileFilterRequest request
            ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .queryParam("minAge", request.minAge())
                .queryParam("maxAge", request.maxAge())
                .queryParam("smoke", request.smoke())
                .queryParam("drink", request.drink())
                .queryParam("religion", request.religion())
                .queryParam("hobbyCode", request.hobbyCode())
                .queryParam("cities", String.join(",", request.cities()))
                .contentType(JSON)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 오늘의_이성_추천_프로필_조회_요청_검증(
            final ExtractableResponse<Response> response,
            final Profile goldMemberProfile,
            final Profile diamondMemberProfile
    ) {
        TodayProfilesResponse todayProfilesResponse = response.as(TodayProfilesResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(todayProfilesResponse.profileRecommendationResponses().size()).isEqualTo(2);
            ProfileRecommendationResponse goldMemberResponse = todayProfilesResponse.profileRecommendationResponses()
                    .get(0);
            softly.assertThat(goldMemberResponse).isNotNull();
            softly.assertThat(goldMemberResponse).usingRecursiveComparison()
                    .isEqualTo(추천_프로필_조회_응답_생성_프로필(goldMemberProfile));
            ProfileRecommendationResponse diamondMemberResponse = todayProfilesResponse.profileRecommendationResponses()
                    .get(1);
            softly.assertThat(diamondMemberResponse).isNotNull();
            softly.assertThat(diamondMemberResponse).usingRecursiveComparison()
                    .isEqualTo(추천_프로필_조회_응답_생성_프로필(diamondMemberProfile));
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected void 이성_회원_프로필_조회_요청_검증(
            final ExtractableResponse<Response> response,
            final Profile profile
    ) {
        ProfileRecommendationResponse profileRecommendationResponse = response.as(ProfileRecommendationResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(profileRecommendationResponse).isNotNull();
            softly.assertThat(profileRecommendationResponse).usingRecursiveComparison()
                    .isEqualTo(추천_프로필_조회_응답_생성_프로필(profile));
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

}
