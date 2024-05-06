package com.atwoz.survey.domain.survey;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    private SurveyAnswer(final String description) {
        this.description = description;
    }

    public static SurveyAnswer from(final String description) {
        return new SurveyAnswer(description);
    }

    public boolean isSame(final Long id) {
        return id.equals(this.id);
    }
}
