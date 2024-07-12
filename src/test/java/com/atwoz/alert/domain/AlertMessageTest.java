package com.atwoz.alert.domain;

import com.atwoz.alert.domain.vo.AlertMessage;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertMessageTest {

    @Test
    void 메시지_정상_생성() {
        // given
        String title = "메시지 제목";
        String body = "메시지 상세 내용";

        // when
        AlertMessage message = AlertMessage.createWith(title, body);

        // then
        assertSoftly(softly -> {
            softly.assertThat(message.getTitle()).isEqualTo(title);
            softly.assertThat(message.getBody()).isEqualTo(body);
        });
    }
}
