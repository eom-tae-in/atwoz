package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertContentSearchResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import com.atwoz.helper.IntegrationHelper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_id_없음;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_제목_날짜_주입;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private AlertQueryRepository alertQueryRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private AlertRepository alertRepository;

    @Nested
    class 알림_조회_정상 {

        @Test
        void 받은_알림_페이징_조회() {
            // given
            Long senderId = 1L;
            List<Alert> alerts = new ArrayList<>();

            for (int day = 1; day <= 10; day++) {
                Alert alert = 알림_생성_제목_날짜_주입("알림 제목 " + day, day);
                LocalDateTime futureTime = LocalDateTime.now().plusDays(day);
                auditingHandler.setDateTimeProvider(() -> Optional.of(futureTime));
                alertRepository.save(alert);
                alerts.add(alert);
            }

            PageRequest pageRequest = PageRequest.of(0, 9);

            // when
            Page<AlertSearchResponse> found = alertQueryRepository.findMemberAlertsWithPaging(senderId, pageRequest);

            // then
            List<AlertSearchResponse> expected = extractAlertResponsesWithLimit(alerts, 9);
            assertSoftly(softly -> {
                softly.assertThat(found).hasSize(9);
                softly.assertThat(found.hasNext()).isTrue();
                softly.assertThat(found.getContent()).isEqualTo(expected);
            });
        }

        private List<AlertSearchResponse> extractAlertResponsesWithLimit(final List<Alert> alerts, final int limit) {
            return alerts.stream()
                    .sorted(Comparator.comparing(Alert::getCreatedAt)
                            .reversed()
                            .thenComparing(Comparator.comparing(Alert::getId).reversed()))
                    .map(alert -> new AlertSearchResponse(
                            alert.getId(),
                            alert.getAlertGroup(),
                            new AlertContentSearchResponse(alert.getTitle(), alert.getBody()),
                            alert.getIsRead(),
                            alert.getCreatedAt()
                    ))
                    .limit(limit)
                    .toList();
        }

        @Test
        void 생성_후_조회() {
            // given
            Long senderId = 1L;
            Long alertId = 1L;
            Alert alert = 알림_생성_id_없음();
            alertRepository.save(alert);

            // when
            Optional<Alert> found = alertQueryRepository.findByMemberIdAndId(senderId, alertId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(found).isNotEmpty();
                softly.assertThat(found.get()).usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(alert);
            });
        }
    }

    @Test
    void 저장되지_않은_알림_id는_조회되지_않는다() {
        // given
        Long senderId = 1L;

        Alert alert = 알림_생성_id_없음();
        Alert savedAlert = alertRepository.save(alert);
        Long alertId = savedAlert.getId() + 1L;

        // when
        Optional<Alert> found = alertQueryRepository.findByMemberIdAndId(senderId, alertId);

        // then
        assertThat(found).isEmpty();
    }
}
