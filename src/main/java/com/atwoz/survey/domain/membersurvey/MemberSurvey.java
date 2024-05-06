package com.atwoz.survey.domain.membersurvey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Long answerId;

    private MemberSurvey(final Long memberId, final Long questionId, final Long answerId) {
        this.memberId = memberId;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public static MemberSurvey of(final Long memberId, final Long questionId, final Long answerId) {
        return new MemberSurvey(memberId, questionId, answerId);
    }
}
