package com.atwoz.member.domain;

import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.profile.ProfileAccessStatus;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberProfileTest {

    @Test
    void 성별_정보로_회원_프로필을_생성한다() {
        // given
        String gender = "남성";

        // when
        MemberProfile memberProfile = MemberProfile.createWith(gender);

        // then
        assertSoftly(softly ->  {
            softly.assertThat(memberProfile.getProfileAccessStatus()).isEqualTo(ProfileAccessStatus.WAITING);
            softly.assertThat(memberProfile.getProfile()).isNotNull();
        });
    }
}
