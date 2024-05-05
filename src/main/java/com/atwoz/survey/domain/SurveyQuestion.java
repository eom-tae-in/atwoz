package com.atwoz.survey.domain;

import com.atwoz.survey.exception.exceptions.SurveyAnswerDuplicatedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<SurveyAnswer> answers;

    private SurveyQuestion(final Survey survey, final String description) {
        this.survey = survey;
        this.description = description;
    }

    public static SurveyQuestion of(final Survey survey, final String description, final List<String> answers) {
        validateAnswersIsNotDuplicated(answers);

        SurveyQuestion surveyQuestion = new SurveyQuestion(survey, description);
        List<SurveyAnswer> surveyAnswers = answers.stream()
                .map(answer -> SurveyAnswer.of(surveyQuestion, answer))
                .toList();
        surveyQuestion.addSurveyAnswers(surveyAnswers);

        return surveyQuestion;
    }

    private static void validateAnswersIsNotDuplicated(final List<String> answers) {
        Set<String> answersSet = new HashSet<>(answers);
        if (answersSet.size() != answers.size()) {
            throw new SurveyAnswerDuplicatedException();
        }
    }

    private void addSurveyAnswers(final List<SurveyAnswer> answers) {
        this.answers.addAll(answers);
    }
}
