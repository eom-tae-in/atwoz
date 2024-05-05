package com.atwoz.survey.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyQuestion question;

    @Column(nullable = false)
    private String description;

    private SurveyAnswer(final SurveyQuestion question, final String description) {
        this.question = question;
        this.description = description;
    }

    public static SurveyAnswer of(final SurveyQuestion question, final String description) {
        return new SurveyAnswer(question, description);
    }
}
