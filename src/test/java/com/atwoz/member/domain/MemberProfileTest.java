package com.atwoz.member.domain;

import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.profile.ProfileAccessStatus;
import com.atwoz.member.exception.exceptions.member.profile.InvalidProfileAccessStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("회원_프로필_접근_상태를_변경한다")
    @Nested
    class ProfileAccessStatusModification{

        private final MemberProfile memberProfile = MemberProfile.createWith("남성");

        @Test
        void 프로필_상태_정보가_유효하면_상태를_변경한다() {
            // given
            String validProfileStatus = "공개";

            // when
            memberProfile.changeProfileAccessStatus(validProfileStatus);

            // then
            assertThat(memberProfile.getProfileAccessStatus()).isEqualTo(ProfileAccessStatus.PUBLIC);
        }

        @Test
        void 프로필_상태_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidProfileStatus = "invalidProfileStatus";

            // when & then
            assertThatThrownBy(() -> memberProfile.changeProfileAccessStatus(invalidProfileStatus))
                    .isInstanceOf(InvalidProfileAccessStatusException.class);
        }
    }
}
