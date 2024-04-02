package com.atwoz.member.domain;

import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberGrade;
import com.atwoz.member.domain.member.MemberRole;
import com.atwoz.member.domain.member.MemberStatus;
import com.atwoz.member.domain.member.profile.ProfileAccessStatus;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_수정_요청서_요청;
import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_초기화_요청서_요청;
import static com.atwoz.member.fixture.domain.member.MemberFixture.OAuth_인증만_완료한_유저_생성;
import static com.atwoz.member.fixture.domain.member.MemberFixture.PASS_인증만_완료한_유저_생성;
import static com.atwoz.member.fixture.domain.member.MemberFixture.어드민_유저_생성;
import static com.atwoz.member.fixture.domain.member.MemberFixture.일반_유저_생성;
import static com.atwoz.member.fixture.domain.member.MemberProfileInfoFixture.회원_프로필_정보_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberTest {

    @Test
    void 어드민인_경우에_true를_반환한다() {
        // given
        Member admin = 어드민_유저_생성();

        // when
        boolean result = admin.isAdmin();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void OAuth_인증을_완료하면_회원이_생성된다() {
        // when
        Member member = OAuth_인증만_완료한_유저_생성();

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getMemberProfile().getProfile().getPhysicalProfile().getGender().getName())
                    .isNotEmpty();
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
            softly.assertThat(member.getMemberRole()).isEqualTo(MemberRole.MEMBER);
            softly.assertThat(member.getMemberProfile().getProfileAccessStatus())
                    .isEqualTo(ProfileAccessStatus.WAITING);
        });
    }

    @Test
    void PASS_인증을_완료하면_회원이_생성된다() {
        // when
        Member member = PASS_인증만_완료한_유저_생성();

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getMemberProfile().getProfile().getPhysicalProfile().getGender().getName())
                    .isNotEmpty();
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
            softly.assertThat(member.getMemberRole()).isEqualTo(MemberRole.MEMBER);
            softly.assertThat(member.getMemberProfile().getProfileAccessStatus())
                    .isEqualTo(ProfileAccessStatus.WAITING);
        });
    }

    @Test
    void 회원_정보를_초기화한다() {
        // given
        Member member = PASS_인증만_완료한_유저_생성();
        MemberInitializeRequest memberInitializeRequest = 회원_정보_초기화_요청서_요청();

        // when
        member.initializeWith(memberInitializeRequest.nickname(), memberInitializeRequest.recommender(),
                회원_프로필_정보_생성(memberInitializeRequest.profileInitializeRequest()));

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getNickname()).isEqualTo(memberInitializeRequest.nickname());
            softly.assertThat(member.getRecommender()).isEqualTo(memberInitializeRequest.recommender());
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberProfile()).isNotNull();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
            softly.assertThat(member.getMemberRole()).isEqualTo(MemberRole.MEMBER);
        });
    }

    @Test
    void 회원_정보를_수정한다() {
        // given
        Member member = 일반_유저_생성();
        MemberUpdateRequest memberUpdateRequest = 회원_정보_수정_요청서_요청();

        // when
        member.updateWith(memberUpdateRequest.nickname(), 회원_프로필_정보_생성(memberUpdateRequest.profileUpdateRequest()));

        // then
        assertSoftly(softly -> {
            softly.assertThat(member.getNickname()).isEqualTo(memberUpdateRequest.nickname());
            softly.assertThat(member.getPhoneNumber()).isNotEmpty();
            softly.assertThat(member.getMemberProfile()).isNotNull();
            softly.assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVE);
            softly.assertThat(member.getMemberGrade()).isEqualTo(MemberGrade.SILVER);
            softly.assertThat(member.getMemberRole()).isEqualTo(MemberRole.MEMBER);
        });
    }
}
