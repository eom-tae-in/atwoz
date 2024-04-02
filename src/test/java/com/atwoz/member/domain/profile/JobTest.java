package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Job;
import com.atwoz.member.exception.exceptions.member.profile.InvalidJobException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JobTest {

    @DisplayName("직업_코드_값으로_Job을_찾는다")
    @Nested
    class JobSearch {

        @Test
        void 직업_코드_값이_유효하면_Job을_찾아_반환한다() {
            // given
            String validJobCode = "A001";

            // when
            Job job = Job.findByCode(validJobCode);

            // then
            assertThat(job.getCode()).isEqualTo(validJobCode);
        }

        @Test
        void 직업_코드_값이_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidJobCode = "invalidJobCode";

            // when & then
            assertThatThrownBy(() -> Job.findByCode(invalidJobCode))
                    .isInstanceOf(InvalidJobException.class);
        }
    }
}
