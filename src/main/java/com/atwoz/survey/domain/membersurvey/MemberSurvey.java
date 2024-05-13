package com.atwoz.survey.domain.membersurvey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class MemberSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Integer answerNumber;

    private MemberSurvey(final Long memberId, final Long questionId, final Integer answerNumber) {
        this.memberId = memberId;
        this.questionId = questionId;
        this.answerNumber = answerNumber;
    }

    public static MemberSurvey of(final Long memberId, final Long questionId, final Integer answerNumber) {
        return new MemberSurvey(memberId, questionId, answerNumber);
    }
}
