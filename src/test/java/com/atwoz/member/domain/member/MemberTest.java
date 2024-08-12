package com.atwoz.member.domain.member;

import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.domain.member.vo.MemberStatus;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성;
import static com.atwoz.member.fixture.member.dto.request.InternalProfileInitializeRequestFixture.내부_프로필_초기화_요청_생성_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.InternalProfileUpdateRequestFixture.내부_프로필_업데이트_요청_생성_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.MemberUpdateRequestFixture.회원_업데이트_요청;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

    private UniqueMemberFieldsGenerator uniqueMemberFieldsGenerator;

    private YearManager yearManager;

    @BeforeEach
    void setup() {
        uniqueMemberFieldsGenerator = new UniqueMemberFieldsGenerator();
        yearManager = new FakeYearManager();
    }

    @Test
    void OAuth_인증을_완료하면_회원이_생성된다() {
        // when
        Member member = OAUth_인증_완료한_회원_생성();

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getMemberProfile().getProfile().getPhysicalProfile().getGender().getName())
                    .isNotEmpty();
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
            softly.assertThat(member.getMemberProfile().getProfileAccessStatus())
                    .isEqualTo(ProfileAccessStatus.WAITING);
        });
    }

    @Test
    void PASS_인증을_완료하면_회원이_생성된다() {
        // when
        Member member = PASS_인증_완료한_회원_생성();

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getMemberProfile().getProfile().getPhysicalProfile().getGender().getName())
                    .isNotEmpty();
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
            softly.assertThat(member.getMemberProfile().getProfileAccessStatus())
                    .isEqualTo(ProfileAccessStatus.WAITING);
        });
    }

    @Test
    void 회원_정보를_초기화한다() {
        // given
        Member member = PASS_인증_완료한_회원_생성();
        String nickname = uniqueMemberFieldsGenerator.generateNickname();
        Long recommenderId = 2L;

        // when
        member.initializeWith(nickname, recommenderId, 내부_프로필_초기화_요청_생성_연도관리자(yearManager));

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getNickname()).isEqualTo(nickname);
            softly.assertThat(member.getRecommenderId()).isEqualTo(recommenderId);
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberProfile()).isNotNull();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
        });
    }

    @Test
    void 회원_정보를_수정한다() {
        // given
        Member member = 회원_생성();
        MemberUpdateRequest memberUpdateRequest = 회원_업데이트_요청();

        // when
        member.updateWith(memberUpdateRequest.nickname(), 내부_프로필_업데이트_요청_생성_연도관리자(yearManager));

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getNickname()).isEqualTo(memberUpdateRequest.nickname());
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberProfile()).isNotNull();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
        });
    }

    private Member OAUth_인증_완료한_회원_생성() {
        return Member.createWithOAuth(uniqueMemberFieldsGenerator.generatePhoneNumber());
    }

    private Member PASS_인증_완료한_회원_생성() {
        return Member.createWithPass("남성", uniqueMemberFieldsGenerator.generatePhoneNumber());
    }
}
