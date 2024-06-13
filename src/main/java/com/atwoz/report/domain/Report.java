package com.atwoz.report.domain;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.report.domain.vo.ReportResult;
import com.atwoz.report.domain.vo.ReportType;
import com.atwoz.report.exception.exceptions.InvalidReporterException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import static com.atwoz.report.domain.vo.ReportResult.WAITING;

@Getter
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reportedUserId;

    @Column(nullable = false)
    private Long reporterId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportResult reportResult;

    @Length(max = 1000)
    private String content;

    public static Report createWith(final Long reportedUserId,
                                    final Long reporterId,
                                    final String reportType,
                                    final String content) {
        validateSameUser(reportedUserId, reporterId);
        return Report.builder()
                .reportedUserId(reportedUserId)
                .reporterId(reporterId)
                .reportType(ReportType.findByCode(reportType))
                .reportResult(WAITING)
                .content(content)
                .build();
    }

    private static void validateSameUser(final Long reportedUserId, final Long reporterId) {
        if (Objects.equals(reportedUserId, reporterId)) {
            throw new InvalidReporterException();
        }
    }

    public void updateReportResult(final String reportResult) {
        this.reportResult = ReportResult.findByName(reportResult);
    }
}
