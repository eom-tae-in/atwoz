package com.atwoz.alert.domain;

import com.atwoz.alert.domain.vo.AlertGroup;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertTest {

    @Nested
    class 알림_정상 {

        @Test
        void 알림_정상_생성() {
            // given
            AlertGroup alertGroup = AlertGroup.MEMBER_LIKE;
            String title = "알림 제목";
            String body = "알림 상세 내용";
            Long receiverId = 1L;

            // when
            Alert alert = Alert.createWith(alertGroup, title, body, receiverId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(alert.isRead()).isEqualTo(false);
                softly.assertThat(alert.getGroup()).isEqualTo(alertGroup.getName());
                softly.assertThat(alert.getTitle()).isEqualTo(title);
                softly.assertThat(alert.getBody()).isEqualTo(body);
            });
        }

        @Test
        void 알림_읽음() {
            // given
            AlertGroup alertGroup = AlertGroup.MEMBER_LIKE;
            String title = "알림 제목";
            String body = "알림 상세 내용";
            Long receiverId = 1L;
            Alert alert = Alert.createWith(alertGroup, title, body, receiverId);

            // when
            alert.read();

            // then
            assertThat(alert.isRead()).isTrue();
        }
    }
}
