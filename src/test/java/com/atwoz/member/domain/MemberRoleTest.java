package com.atwoz.member.domain;

import com.atwoz.member.domain.member.MemberRole;
import com.atwoz.member.exception.exceptions.member.RoleNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberRoleTest {

    @DisplayName("권한_정보로_MemberRole을_찾는다")
    @Nested
    class MemberRoleSearch {

        @Test
        void 권한_정보가_유효하면_MemberRole을_찾아_반환한다() {
            // given
            String validRole = "member";

            // when
            MemberRole memberRole = MemberRole.from(validRole);

            // then
            assertThat(memberRole.getRole()).isEqualTo(validRole);
        }

        @Test
        void 권한_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidRole = "invalidRole";

            // when & then
            assertThatThrownBy(() -> MemberRole.from(invalidRole))
                    .isInstanceOf(RoleNotFoundException.class);
        }
    }
}
