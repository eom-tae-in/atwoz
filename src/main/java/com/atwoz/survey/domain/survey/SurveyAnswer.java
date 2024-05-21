package com.atwoz.survey.domain.survey;

import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerNumberRangeException;
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
@EqualsAndHashCode(of = {"number", "description"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class SurveyAnswer {

    private static final int MINIMUM_ANSWER_NUMBER = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String description;

    private SurveyAnswer(final Integer number, final String description) {
        this.number = number;
        this.description = description;
    }

    public static SurveyAnswer of(final Integer number, final String description) {
        validateNumberRange(number);
        return new SurveyAnswer(number, description);
    }

    private static void validateNumberRange(final int number) {
        if (number < MINIMUM_ANSWER_NUMBER) {
            throw new SurveyAnswerNumberRangeException();
        }
    }

    public boolean isSame(final Integer number) {
        return number.equals(this.number);
    }
}
