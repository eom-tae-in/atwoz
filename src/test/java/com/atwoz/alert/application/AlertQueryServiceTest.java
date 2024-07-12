package com.atwoz.alert.application;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.FakeAlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertContentSearchResponse;
import com.atwoz.alert.infrastructure.dto.AlertPagingResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_제목_날짜_id_주입;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertQueryServiceTest {

    private AlertQueryService alertQueryService;
    private AlertRepository alertRepository;

    @BeforeEach
    void init() {
        alertRepository = new FakeAlertRepository();
        alertQueryService = new AlertQueryService(alertRepository);
    }

    @Test
    void 받은_알림들을_페이징_조회한다() {
        // given
        Long memberId = 1L;
        List<Alert> alerts = new ArrayList<>();
        PageRequest request = PageRequest.of(0, 9);

        for (int day = 1; day <= 10; day++) {
            Alert alert = 알림_생성_제목_날짜_id_주입("알림 제목 " + day, day, day);
            alerts.add(alert);
            alertRepository.save(alert);
        }

        // when
        AlertPagingResponse response = alertQueryService.findMemberAlertsWithPaging(memberId, request);
        List<AlertSearchResponse> expected = extractAlertResponsesWithLimit(alerts, 9);

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.alerts()).hasSize(9);
            softly.assertThat(response.nextPage()).isEqualTo(1);
            softly.assertThat(response.alerts()).isEqualTo(expected);
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
}
