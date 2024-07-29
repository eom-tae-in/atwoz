package com.atwoz.alert.domain;

import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.exception.exceptions.AlertGroupNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertGroupTest {

    @Nested
    class 그룹_조회 {

        @Test
        void 그룹이_유효하면_정상_반환한다() {
            // given
            String groupName = "좋아요";

            // when
            AlertGroup targetGroup = AlertGroup.findByName(groupName);

            // then
            assertThat(targetGroup.getName()).isEqualTo(groupName);
        }

        @Test
        void 그룹이_유효하지_않으면_예외가_발생한다() {
            // given
            String groupName = "-123";

            // when & then
            assertThatThrownBy(() -> AlertGroup.findByName(groupName))
                    .isInstanceOf(AlertGroupNotFoundException.class);
        }
    }
}
