package com.atwoz.member.domain.selfintro;

import com.atwoz.member.exception.exceptions.selfintro.InvalidContentException;
import com.atwoz.member.exception.exceptions.selfintro.WriterNotEqualsException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.selfintro.SelfIntroFixture.셀프_소개글_생성_id_있음;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SelfIntroTest {

    @Nested
    class 셀프_소개글_생성 {

        @Test
        void 셀프_소개글을_생성한다() {
            // given
            Long memberId = 1L;
            String content = "안녕하세요~";

            // when
            SelfIntro selfIntro = SelfIntro.createWith(memberId, content);

            // then
            assertSoftly(softly -> {
                softly.assertThat(selfIntro.getMemberId()).isEqualTo(memberId);
                softly.assertThat(selfIntro.getContent()).isEqualTo(content);
            });
        }

        @Test
        void 소개글이_올바르게_입력되지_않으면_예외가_발생한다() {
            // given
            Long memberId = 1L;
            String invalidContent = "";

            // when & then
            assertThatThrownBy(() -> SelfIntro.createWith(memberId, invalidContent))
                    .isInstanceOf(InvalidContentException.class);
        }
    }

    @Nested
    class 셀프_소개글_수정 {

        @Test
        void 셀프_소개글을_수정한다() {
            // given
            SelfIntro selfIntro = SelfIntro.createWith(1L, "안녕하세요~");
            String updateContent = "반가워요~";

            // when
            selfIntro.update(updateContent);

            // then
            assertSoftly(softly -> {
                softly.assertThat(selfIntro.getMemberId()).isEqualTo(1L);
                softly.assertThat(selfIntro.getContent()).isEqualTo(updateContent);
            });
        }

        @Test
        void 소개글이_올바르게_입력되지_않으면_예외가_발생한다() {
            // given
            SelfIntro selfIntro = SelfIntro.createWith(1L, "안녕하세요~");
            String invalidUpdateContent = "";

            // when & then
            assertThatThrownBy(() -> selfIntro.update(invalidUpdateContent))
                    .isInstanceOf(InvalidContentException.class);
        }
    }

    @Test
    void 소개글_작성자와_요청을_보낸_요청자_id가_다르면_예외가_발생한다() {
        // given
        SelfIntro selfIntro = 셀프_소개글_생성_id_있음(1L);
        Long anotherMemberId = 2L;

        // when & then
        assertThatThrownBy(() -> selfIntro.validateSameWriter(anotherMemberId))
                .isInstanceOf(WriterNotEqualsException.class);
    }
}
