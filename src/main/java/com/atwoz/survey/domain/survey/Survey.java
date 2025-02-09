package com.atwoz.survey.domain.survey;

import com.atwoz.survey.domain.survey.dto.SurveyComparisonRequest;
import com.atwoz.survey.domain.survey.dto.SurveyCreateDto;
import com.atwoz.survey.domain.survey.dto.SurveyQuestionCreateDto;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionSubmitSizeNotMatchException;
import com.atwoz.survey.exception.survey.exceptions.SurveyQuestionDuplicatedException;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean required;

    @JoinColumn(name = "survey_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<SurveyQuestion> questions = new ArrayList<>();

    private Survey(final String name, final Boolean required) {
        this.name = name;
        this.required = required;
    }

    public static Survey from(final SurveyCreateDto request) {
        validateQuestionsNotDuplicated(request.questions());

        Survey survey = new Survey(request.name(), request.required());
        survey.addSurveyQuestions(request.questions());

        return survey;
    }

    private static void validateQuestionsNotDuplicated(final List<SurveyQuestionCreateDto> questionRequests) {
        List<String> questions = questionRequests.stream()
                .map(SurveyQuestionCreateDto::description)
                .toList();

        Set<String> questionsSet = new HashSet<>(questions);
        if (questionsSet.size() != questions.size()) {
            throw new SurveyQuestionDuplicatedException();
        }
    }

    private void addSurveyQuestions(final List<SurveyQuestionCreateDto> questionRequests) {
        List<SurveyQuestion> questions = questionRequests.stream()
                .map(request -> SurveyQuestion.of(request.description(), request.answers()))
                .toList();
        this.questions.addAll(questions);
    }

    public void validateIsValidSubmitSurveyRequest(final SurveyComparisonRequest request) {
        if (request.questions().size() != questions.size()) {
            throw new SurveyQuestionSubmitSizeNotMatchException();
        }
        questions.forEach(question -> question.validateSubmitAnswer(request.questions()));
    }

    public boolean isSameName(final String name) {
        return name.equals(this.name);
    }

    public boolean isRequired() {
        return required;
    }
}
