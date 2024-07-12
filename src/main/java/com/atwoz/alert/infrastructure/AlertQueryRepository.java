package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.infrastructure.dto.AlertContentSearchResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import static com.atwoz.alert.domain.QAlert.alert;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class AlertQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<AlertSearchResponse> findMemberAlertsWithPaging(final Long memberId, final Pageable pageable) {
        List<AlertSearchResponse> results = jpaQueryFactory.select(
                        constructor(AlertSearchResponse.class,
                                alert.id,
                                alert.alertGroup,
                                constructor(AlertContentSearchResponse.class,
                                        alert.alertMessage.title,
                                        alert.alertMessage.body),
                                alert.isRead,
                                alert.createdAt))
                .from(alert)
                .where(alert.receiverId.eq(memberId), alert.deletedAt.isNull())
                .orderBy(alert.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, results.size());
    }

    public Optional<Alert> findByMemberIdAndId(final Long memberId, final Long id) {
        Alert findAlert = jpaQueryFactory.select(alert)
                .from(alert)
                .where(alert.id.eq(id), alert.receiverId.eq(memberId))
                .fetchOne();
        return Optional.ofNullable(findAlert);
    }
}
