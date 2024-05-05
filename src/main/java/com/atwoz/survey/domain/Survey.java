package com.atwoz.survey.domain;

import com.atwoz.survey.application.dto.SurveyCreateRequest;
import com.atwoz.survey.application.dto.SurveyQuestionCreateRequest;
import com.atwoz.survey.exception.exceptions.SurveyQuestionDuplicatedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean required;

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<SurveyQuestion> questions = new ArrayList<>();

    private Survey(final String name, final Boolean required) {
        this.name = name;
        this.required = required;
    }

    public static Survey createWith(final SurveyCreateRequest request) {
        validateQuestionsIsNotDuplicated(request.questions());

        Survey survey = new Survey(request.surveyName(), request.required());
        survey.addSurveyQuestions(request.questions());

        return survey;
    }

    private static void validateQuestionsIsNotDuplicated(final List<SurveyQuestionCreateRequest> requests) {
        List<String> questions = requests.stream()
                .map(SurveyQuestionCreateRequest::description)
                .toList();

        Set<String> questionsSet = new HashSet<>(questions);
        if (questionsSet.size() != questions.size()) {
            throw new SurveyQuestionDuplicatedException();
        }
    }

    private void addSurveyQuestions(final List<SurveyQuestionCreateRequest> questionRequests) {
        List<SurveyQuestion> questions = questionRequests.stream()
                .map(request -> SurveyQuestion.of(this, request.description(), request.answers()))
                .toList();
        this.questions.addAll(questions);
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public void updateRequired(final Boolean required) {
        this.required = required;
    }
}
