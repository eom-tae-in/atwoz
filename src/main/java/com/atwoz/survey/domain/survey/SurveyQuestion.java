package com.atwoz.survey.domain.survey;

import com.atwoz.survey.domain.survey.dto.SurveyQuestionComparisonRequest;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAnswerInvalidSubmitException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionNotSubmittedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = {"description", "answers"})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @JoinColumn(name = "survey_question_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<SurveyAnswer> answers = new ArrayList<>();

    private SurveyQuestion(final String description) {
        this.description = description;
    }

    public static SurveyQuestion of(final String description, final List<String> answers) {
        validateAnswersIsNotDuplicated(answers);

        SurveyQuestion surveyQuestion = new SurveyQuestion(description);
        List<SurveyAnswer> surveyAnswers = answers.stream()
                .map(SurveyAnswer::from)
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

    public void validateIsValidSubmitAnswer(final List<SurveyQuestionComparisonRequest> requests) {
        SurveyQuestionComparisonRequest questionRequest = findSurveyQuestionById(requests);
        if (!isContainsSameAnswer(questionRequest.answerId())) {
            throw new SurveyAnswerInvalidSubmitException();
        }
    }

    private SurveyQuestionComparisonRequest findSurveyQuestionById(final List<SurveyQuestionComparisonRequest> requests) {
        return requests
                .stream()
                .filter(question -> id.equals(question.questionId()))
                .findAny()
                .orElseThrow(SurveyQuestionNotSubmittedException::new);
    }

    private boolean isContainsSameAnswer(final Long answerId) {
        return answers.stream()
                .anyMatch(answer -> answer.isSame(answerId));
    }
}
