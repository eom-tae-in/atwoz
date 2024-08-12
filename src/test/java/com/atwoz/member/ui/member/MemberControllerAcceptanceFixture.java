package com.atwoz.member.ui.member;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.application.member.dto.initial.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.MemberHobby;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import com.atwoz.member.ui.member.dto.TodayProfilesResponse;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원등급_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_도시_지역_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_방문날짜_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_종교_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.dto.request.MemberInitializeRequestFixture.회원_초기화_요청_닉네임_취미코드목록_스타일코드목록;
import static com.atwoz.member.fixture.member.dto.request.MemberUpdateRequestFixture.회원_업데이트_요청_취미코드목록_스타일코드목록;
import static com.atwoz.member.fixture.member.dto.response.MemberResponseFixture.회원_정보_응답서_요청;
import static com.atwoz.member.fixture.member.dto.response.ProfileResponseFixture.프로필_응답서_생성_회원;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private MemberTokenProvider memberTokenProvider;

    private UniqueMemberFieldsGenerator uniqueMemberFieldsGenerator;

    private List<Hobby> hobbies;

    private List<Style> styles;

    @BeforeEach
    void init() {
        uniqueMemberFieldsGenerator = new UniqueMemberFieldsGenerator();
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "code1"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
    }

    protected Member 일반_회원_생성() {
        return memberRepository.save(회원_생성_취미목록_스타일목록(
                hobbies,
                styles
        ));
    }

    protected Member 다이아_등급_이성_회원_생성() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();

        return memberRepository.save(회원_생성_닉네임_전화번호_회원등급_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                nickname,
                phoneNumber,
                MemberGrade.DIAMOND,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                hobbies,
                styles
        ));
    }

    protected Member 골드_등급_이성_회원_생성() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();

        return memberRepository.save(회원_생성_닉네임_전화번호_회원등급_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                nickname,
                phoneNumber,
                MemberGrade.GOLD,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                hobbies,
                styles
        ));
    }

    protected Member 인기있는_회원_생성(final Member sender) {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();
        Member receiver = 회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                nickname,
                phoneNumber,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                hobbies,
                styles
        );
        memberRepository.save(receiver);
        MemberLike memberLike = MemberLike.builder()
                .senderId(sender.getId())
                .receiverId(receiver.getId())
                .isRecent(false)
                .build();
        memberLikeRepository.save(memberLike);

        return receiver;
    }

    protected Member 오늘_방문한_이성_회원_생성() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();
        return memberRepository.save(회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_방문날짜_취미목록_스타일목록(
                nickname,
                phoneNumber,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                LocalDate.now(),
                hobbies,
                styles
        ));
    }

    protected Member 근처에_있는_이성_회원_생성(final Member member) {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();
        Location location = member.getMemberProfile()
                .getProfile()
                .getLocation();

        return memberRepository.save(회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_도시_지역_취미목록_스타일목록(
                nickname,
                phoneNumber,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                location.getCity(),
                location.getSector(),
                hobbies,
                styles
        ));
    }

    protected Member 최근에_가입한_이성_회원_생성() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();

        return memberRepository.save(회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                nickname,
                phoneNumber,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                hobbies,
                styles
        ));
    }

    protected Member 종교가_같은_이성_회원_생성(final Member member) {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();
        Profile profile = member.getMemberProfile()
                .getProfile();

        return memberRepository.save(회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_종교_취미목록_스타일목록(
                nickname,
                phoneNumber,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                profile.getReligion(),
                hobbies,
                styles
        ));
    }

    protected Member 취미가_같은_이성_회원_생성(final Member member) {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();
        List<Hobby> memberHobbies = member.getMemberProfile()
                .getProfile()
                .getMemberHobbies()
                .getHobbies()
                .stream()
                .map(MemberHobby::getHobby)
                .toList();

        return memberRepository.save(회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                nickname,
                phoneNumber,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                memberHobbies,
                styles
        ));
    }

    protected String 토큰_생성(final Member member) {
        return memberTokenProvider.createAccessToken(member.getId());
    }

    protected String 회원_닉네임을_요청한다() {
        return uniqueMemberFieldsGenerator.generateNickname();
    }

    protected ExtractableResponse<Response> 회원_닉네임_중복을_확인한다(
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

    protected void 회원_닉네임_중복_확인_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected MemberInitializeRequest 회원_초기_정보를_요청한다() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();

        return 회원_초기화_요청_닉네임_취미코드목록_스타일코드목록(
                nickname,
                hobbies.stream()
                        .map(Hobby::getCode)
                        .toList(),
                styles.stream()
                        .map(Style::getCode)
                        .toList()
        );
    }

    protected ExtractableResponse<Response> 회원_정보_초기화_요청(
            final String url,
            final String token,
            final MemberInitializeRequest memberInitializeRequest
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(memberInitializeRequest)
                .when().log().all()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_정보_초기화_검증(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    protected ExtractableResponse<Response> 회원_조회_요청(
            final String url,
            final String token,
            final Member member
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .pathParam("memberId", member.getId())
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 회원_정보_조회_검증(final ExtractableResponse<Response> response,
                               final Member member) {
        MemberResponse memberResponse = response.as(MemberResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(memberResponse).isNotNull();
            softly.assertThat(memberResponse).usingRecursiveComparison()
                    .isEqualTo(회원_정보_응답서_요청(member));
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected ExtractableResponse<Response> 프로필_조회_요청(final String url, final String token) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .when().log().all()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 오늘의_이성_추천_프로필_조회_요청_검증(
            final ExtractableResponse<Response> response,
            final Member goldMember,
            final Member diamondMember
    ) {
        TodayProfilesResponse todayProfilesResponse = response.as(TodayProfilesResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(todayProfilesResponse.profileResponses().size()).isEqualTo(2);
            ProfileResponse goldMemberResponse = todayProfilesResponse.profileResponses().get(0);
            softly.assertThat(goldMemberResponse).isNotNull();
            softly.assertThat(goldMemberResponse).usingRecursiveComparison()
                    .isEqualTo(프로필_응답서_생성_회원(goldMember));
            ProfileResponse diamondMemberResponse = todayProfilesResponse.profileResponses().get(1);
            softly.assertThat(diamondMemberResponse).isNotNull();
            softly.assertThat(diamondMemberResponse).usingRecursiveComparison()
                    .isEqualTo(프로필_응답서_생성_회원(diamondMember));
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected void 이성_회원_프로필_조회_요청_검증(final ExtractableResponse<Response> response, final Member member) {
        ProfileResponse profileResponse = response.as(ProfileResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(profileResponse).isNotNull();
            softly.assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(프로필_응답서_생성_회원(member));
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        });
    }

    protected MemberUpdateRequest 회원_업데이트_요청서_요청() {
        return 회원_업데이트_요청_취미코드목록_스타일코드목록(
                hobbies.stream()
                        .map(Hobby::getCode)
                        .toList(),
                styles.stream()
                        .map(Style::getCode)
                        .toList()
        );
    }

    protected ExtractableResponse<Response> 회원_정보_수정_요청(
            final String uri,
            final String token,
            final MemberUpdateRequest memberUpdateRequest
    ) {
        return RestAssured.given().log().all()
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(memberUpdateRequest)
                .when().log().all()
                .patch(uri)
                .then().log().all()
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
