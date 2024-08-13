package com.atwoz.survey.domain.membersurvey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class MemberSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long surveyId;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private Integer answerNumber;

    public static MemberSurvey of(final Long surveyId, final Long questionId, final Integer answerNumber) {
        return MemberSurvey.builder()
                .surveyId(surveyId)
                .questionId(questionId)
                .answerNumber(answerNumber)
                .build();
    }
}
