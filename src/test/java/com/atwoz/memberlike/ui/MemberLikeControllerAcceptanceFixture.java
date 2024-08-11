package com.atwoz.memberlike.ui;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.auth.MemberJwtTokenProvider;
import com.atwoz.memberlike.application.dto.MemberLikeCreateRequest;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikePagingResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_주입;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberLikeControllerAcceptanceFixture extends IntegrationHelper {

    @Autowired
    private MemberJwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

    private List<Hobby> hobbies;

    private List<Style> styles;

    private UniqueMemberFieldsGenerator uniqueMemberFieldsGenerator;

    @BeforeEach
    void init() {
        uniqueMemberFieldsGenerator = new UniqueMemberFieldsGenerator();
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "code1"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
    }

    protected Member 초기_회원_생성() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();

        return memberRepository.save(회원_생성_닉네임_전화번호_취미목록_스타일목록(
                nickname,
                phoneNumber,
                hobbies,
                styles
        ));
    }

    protected Member 회원_생성() {
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        String phoneNumber = uniqueMemberFieldsGenerator.generatePhoneNumber();

        return memberRepository.save(회원_생성_닉네임_전화번호_취미목록_스타일목록(
                nickname,
                phoneNumber,
                hobbies,
                styles
        ));
    }

    protected String 토큰_생성(final Member member) {
        return jwtTokenProvider.createAccessToken(member.getId());
    }

    protected void 보낸_호감_목록_생성(final Long memberId) {
        for (long id = 2L; id <= 10L; id++) {
            Member receiver = 회원_생성();
            MemberLike memberLike = 호감_생성_id_주입(memberId, receiver.getId());
            memberLikeRepository.save(memberLike);
        }
    }

    protected void 받은_호감_목록_생성(final Long memberId) {
        for (long id = 2L; id <= 10L; id++) {
            Member sender = 회원_생성();
            MemberLike memberLike = 호감_생성_id_주입(sender.getId(), memberId);
            memberLikeRepository.save(memberLike);
        }
    }

    protected ExtractableResponse 호감을_보낸다(final String token, final String url, final MemberLikeCreateRequest request) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(JSON)
                .body(request)
                .when()
                .post(url)
                .then().log().all()
                .extract();
    }

    protected void 호감_전송_결과_검증(final ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    protected ExtractableResponse 보낸_호감을_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 보낸_호감_조회_결과_검증(final ExtractableResponse response) {
        MemberLikePagingResponse result = response.as(MemberLikePagingResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.memberLikes().size()).isEqualTo(2);
            softly.assertThat(result.nextPage()).isEqualTo(1);
        });
    }

    protected ExtractableResponse 받은_호감을_조회한다(final String token, final String url) {
        return RestAssured.given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .when()
                .get(url)
                .then().log().all()
                .extract();
    }

    protected void 받은_호감_조회_결과_검증(final ExtractableResponse response) {
        MemberLikePagingResponse result = response.as(MemberLikePagingResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            softly.assertThat(result.memberLikes().size()).isEqualTo(2);
            softly.assertThat(result.nextPage()).isEqualTo(1);
        });
    }
}
