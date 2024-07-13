package com.atwoz.alert.application;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.domain.AlertTokenRepository;
import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.exception.exceptions.AlertNotFoundException;
import com.atwoz.alert.exception.exceptions.ReceiverTokenNotFoundException;
import com.atwoz.alert.infrastructure.AlertFakeManager;
import com.atwoz.alert.infrastructure.AlertFakeRepository;
import com.atwoz.alert.infrastructure.AlertFakeTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_id_없음;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_id_있음;
import static com.atwoz.alert.fixture.AlertFixture.옛날_알림_생성;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertServiceTest {

    private AlertService alertService;
    private AlertRepository alertRepository;
    private AlertTokenRepository tokenRepository;
    private AlertManager alertManager;

    @BeforeEach
    void init() {
        alertRepository = new AlertFakeRepository();
        tokenRepository = new AlertFakeTokenRepository();
        alertManager = new AlertFakeManager();
        alertService = new AlertService(alertRepository, tokenRepository, alertManager);
    }

    @Nested
    class 토큰_관리 {

        @Test
        void 토큰을_저장한다() {
            // given
            Long id = 1L;
            String token = "token";

            // when
            alertService.saveToken(id, token);

            // then
            assertSoftly(softly -> {
                softly.assertThat(tokenRepository.hasKey(id)).isTrue();
                softly.assertThat(tokenRepository.getToken(id)).isEqualTo(token);
            });
        }

        @Test
        void 토큰을_저장하면_조회_시_가져올_수_있다() {
            // given
            Long id = 1L;
            String token = "token";

            // when
            alertService.saveToken(id, token);

            // then
            assertDoesNotThrow(() -> tokenRepository.getToken(id));
        }

        @Test
        void 저장되지_않은_토큰을_조회하면_예외가_발생한다() {
            // given
            Long id = 1L;
            Long otherId = 2L;
            String token = "token";

            // when
            alertService.saveToken(id, token);

            // then
            assertThatThrownBy(() -> tokenRepository.getToken(otherId))
                    .isInstanceOf(ReceiverTokenNotFoundException.class);
        }
    }

    @Nested
    class 알림_전송_관리 {

        @Test
        void 알림_전송_정상() {
            // given
            AlertGroup group = AlertGroup.ALERT;
            String title = "알림 제목";
            String body = "알림 상세 내용";
            String sender = "보낸 사람 닉네임";
            Long id = 1L;
            Long alertId = 1L;
            String token = "token";
            alertService.saveToken(id, token);

            // when
            alertService.sendAlert(group, title, body, sender, id);

            // then
            assertSoftly(softly -> {
                Optional<Alert> found = alertRepository.findByMemberIdAndId(id, alertId);
                softly.assertThat(found).isNotEmpty();
                softly.assertThat(found.get()).usingRecursiveComparison()
                        .ignoringActualNullFields()
                        .isEqualTo(알림_생성_id_있음());
            });
        }

        @Test
        void 알림_전송_시_저장되지_않은_토큰을_쓰면_예외가_발생한다() {
            // given
            AlertGroup group = AlertGroup.ALERT;
            String title = "알림 제목";
            String body = "알림 상세 내용";
            String sender = "보낸 사람 닉네임";
            Long id = 1L;

            // when & then
            assertThatThrownBy(() -> alertService.sendAlert(group, title, body, sender, id))
                            .isInstanceOf(ReceiverTokenNotFoundException.class);
        }
    }

    @Nested
    class 알림_단일_조회_관리 {

        @Test
        void 알림_단일_조회_정상() {
            // given
            AlertGroup group = AlertGroup.ALERT;
            String title = "알림 제목";
            String body = "알림 상세 내용";
            String sender = "보낸 사람 닉네임";
            Long id = 1L;
            Long alertId = 1L;
            String token = "token";
            alertService.saveToken(id, token);
            alertService.sendAlert(group, title, body, sender, id);

            // when
            alertService.readAlert(id, alertId);

            // then
            Optional<Alert> savedAlert = alertRepository.findByMemberIdAndId(id, alertId);
            assertSoftly(softly -> {
                softly.assertThat(savedAlert).isPresent();
                Alert alert = savedAlert.get();
                softly.assertThat(alert.isRead()).isTrue();
            });
        }

        @Test
        void 존재하지_않는_알림을_조회할_경우_예외가_발생한다() {
            // given
            AlertGroup group = AlertGroup.ALERT;
            String title = "알림 제목";
            String body = "알림 상세 내용";
            String sender = "보낸 사람 닉네임";
            Long id = 1L;
            Long otherId = 2L;
            String token = "token";
            alertService.saveToken(id, token);
            alertService.sendAlert(group, title, body, sender, id);

            // when & then
            assertThatThrownBy(() -> alertService.readAlert(id, otherId))
                    .isInstanceOf(AlertNotFoundException.class);
        }
    }

    @Test
    void 생성된_지_60일을_초과한_알림은_삭제_상태로_된다() {
        // given
        Long memberId = 1L;
        Alert savedAlert = alertRepository.save(알림_생성_id_없음());
        Alert savedOldAlert = alertRepository.save(옛날_알림_생성());

        // when
        alertService.deleteExpiredAlerts();

        // then
        Optional<Alert> foundSavedAlert = alertRepository.findByMemberIdAndId(memberId, savedAlert.getId());
        Optional<Alert> foundSavedOldAlert = alertRepository.findByMemberIdAndId(memberId, savedOldAlert.getId());

        assertSoftly(softly -> {
            softly.assertThat(foundSavedAlert).isPresent();
            softly.assertThat(foundSavedOldAlert).isEmpty();
        });
    }
}
