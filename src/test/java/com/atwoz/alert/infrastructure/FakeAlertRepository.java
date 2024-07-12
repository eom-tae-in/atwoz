package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertContentSearchResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeAlertRepository implements AlertRepository {

    private static final int DELETION_THRESHOLD_DATE = 61;

    private final Map<Long, Alert> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Alert save(final Alert alert) {
        Alert savedAlert = Alert.builder()
                .id(id)
                .isRead(alert.getIsRead())
                .alertMessage(alert.getAlertMessage())
                .alertGroup(alert.getAlertGroup())
                .createdAt(alert.getCreatedAt())
                .updatedAt(alert.getUpdatedAt())
                .deletedAt(alert.getDeletedAt())
                .build();
        map.put(id, savedAlert);

        return map.get(id++);
    }

    @Override
    public Page<AlertSearchResponse> findMemberAlertsWithPaging(Long memberId, Pageable pageable) {
        List<AlertSearchResponse> alertResponses = map.values()
                .stream()
                .filter(alert -> memberId.equals(alert.getReceiverId()))
                .sorted(Comparator.comparing(Alert::getCreatedAt)
                        .reversed())
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(alert -> new AlertSearchResponse(
                        alert.getId(),
                        alert.getAlertGroup(),
                        new AlertContentSearchResponse(alert.getTitle(), alert.getBody()),
                        alert.getIsRead(),
                        alert.getCreatedAt()
                ))
                .toList();

        return new PageImpl<>(alertResponses, pageable, map.size());
    }

    @Override
    public Optional<Alert> findByMemberIdAndId(final Long memberId, final Long id) {
        return map.values()
                .stream()
                .filter(alert -> isSameTargetAlert(alert, memberId, id))
                .findAny();
    }

    private boolean isSameTargetAlert(final Alert alert, final Long memberId, final Long id) {
        return memberId.equals(alert.getReceiverId()) && id.equals(alert.getId());
    }

    @Override
    public void deleteExpiredAlerts() {
        map.values()
                .stream()
                .filter(this::isExpiredAlert)
                .forEach(this::convertDeleted);
    }

    private boolean isExpiredAlert(final Alert alert) {
        LocalDateTime sixtyDaysAgo = LocalDateTime.now()
                .minusDays(DELETION_THRESHOLD_DATE);

        return alert.getCreatedAt()
                .isBefore(sixtyDaysAgo);
    }

    private void convertDeleted(final Alert alert) {
        Alert deletedAlert = Alert.builder()
                .id(alert.getId())
                .isRead(alert.getIsRead())
                .alertMessage(alert.getAlertMessage())
                .alertGroup(alert.getAlertGroup())
                .createdAt(alert.getCreatedAt())
                .updatedAt(alert.getUpdatedAt())
                .deletedAt(LocalDateTime.now())
                .build();
        map.put(alert.getId(), deletedAlert);
    }
}
