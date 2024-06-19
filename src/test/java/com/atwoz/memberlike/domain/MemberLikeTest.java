package com.atwoz.memberlike.domain;

import com.atwoz.memberlike.domain.vo.LikeIcon;
import com.atwoz.memberlike.domain.vo.LikeLevel;
import com.atwoz.memberlike.exception.exceptions.InvalidMemberLikeException;
import com.atwoz.memberlike.exception.exceptions.LikeTypeNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberLikeTest {

    @Test
    void 호감_정상_생성() {
        // given
        Long senderId = 1L;
        Long receiverId = 2L;
        String likeLevel = "관심있어요";

        // when & then
        assertDoesNotThrow(() -> MemberLike.createWith(senderId, receiverId, likeLevel));
    }

    @Test
    void 호감_값_정상() {
        // given
        Long senderId = 1L;
        Long receiverId = 2L;
        String likeLevel = "관심있어요";
        LikeLevel expectedLevel = LikeLevel.DEFAULT_LIKE;
        LikeIcon expectedIcon = LikeIcon.NONE;

        // when
        MemberLike memberLike = MemberLike.createWith(senderId, receiverId, likeLevel);

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberLike.getSenderId()).isEqualTo(senderId);
            softly.assertThat(memberLike.getReceiverId()).isEqualTo(receiverId);
            softly.assertThat(memberLike.getLikeLevel()).isEqualTo(expectedLevel);
            softly.assertThat(memberLike.getLikeIcon()).isEqualTo(expectedIcon);
            softly.assertThat(memberLike.getIsRecent()).isEqualTo(true);
        });
    }

    @Nested
    class 호감_생성_예외 {

        @Test
        void 자신에게_호감을_보낼_수_없다() {
            // given
            Long senderId = 1L;
            Long receiverId = 1L;
            String likeLevel = "관심있어요";

            // when & then
            assertThatThrownBy(() -> MemberLike.createWith(senderId, receiverId, likeLevel))
                    .isInstanceOf(InvalidMemberLikeException.class);
        }

        @Test
        void 없는_호감_레벨이면_예외가_발생한다() {
            // given
            Long senderId = 1L;
            Long receiverId = 2L;
            String likeLevel = "abc";

            // when & then
            assertThatThrownBy(() -> MemberLike.createWith(senderId, receiverId, likeLevel))
                    .isInstanceOf(LikeTypeNotFoundException.class);
        }
    }
}
