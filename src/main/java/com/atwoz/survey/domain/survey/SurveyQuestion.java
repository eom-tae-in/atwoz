package com.atwoz.survey.domain.survey;

import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;
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

    public static SurveyQuestion of(final String description, final List<SurveyAnswerCreateRequest> answers) {
        validateIsAnswersNotDuplicated(answers);

        SurveyQuestion surveyQuestion = new SurveyQuestion(description);
        surveyQuestion.addSurveyAnswers(answers);

        return surveyQuestion;
    }

    private static void validateIsAnswersNotDuplicated(final List<SurveyAnswerCreateRequest> answers) {
        validateIsAnswerNumbersNotDuplicated(answers);
        validateIsAnswerDescriptionsNotDuplicated(answers);
    }

    private static void validateIsAnswerNumbersNotDuplicated(final List<SurveyAnswerCreateRequest> answers) {
        List<Integer> answerNumbers = answers.stream()
                .map(SurveyAnswerCreateRequest::number)
                .toList();
        Set<Integer> answersSet = new HashSet<>(answerNumbers);
        if (answersSet.size() != answers.size()) {
            throw new SurveyAnswerDuplicatedException();
        }
    }

    private static void validateIsAnswerDescriptionsNotDuplicated(final List<SurveyAnswerCreateRequest> answers) {
        List<String> answerDescriptions = answers.stream()
                .map(SurveyAnswerCreateRequest::answer)
                .toList();
        Set<String> answersSet = new HashSet<>(answerDescriptions);
        if (answersSet.size() != answers.size()) {
            throw new SurveyAnswerDuplicatedException();
        }
    }

    private void addSurveyAnswers(final List<SurveyAnswerCreateRequest> answers) {
        List<SurveyAnswer> surveyAnswers = answers.stream()
                .map(answer -> SurveyAnswer.of(answer.number(), answer.answer()))
                .toList();
        this.answers.addAll(surveyAnswers);
    }

    public void validateIsValidSubmitAnswer(final List<SurveyQuestionComparisonRequest> requests) {
        SurveyQuestionComparisonRequest questionRequest = findSurveyQuestionById(requests);
        if (!isContainsSameAnswer(questionRequest.answerNumber())) {
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

    private boolean isContainsSameAnswer(final Integer answerNumber) {
        return answers.stream()
                .anyMatch(answer -> answer.isSame(answerNumber));
    }
}
