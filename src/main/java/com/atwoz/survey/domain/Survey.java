package com.atwoz.survey.domain;

import com.atwoz.survey.exception.exceptions.QuestionDescriptionDuplicatedException;
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

    public static Survey createWith(final String name, final Boolean required) {
        return new Survey(name, required);
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public void updateRequired(final Boolean required) {
        this.required = required;
    }

    public void addSurveyQuestions(final List<String> descriptions) {
        validateDoesNotHaveDuplicateQuestion(descriptions);

        List<SurveyQuestion> questions = descriptions.stream()
                .map(description -> SurveyQuestion.of(this, description))
                .toList();
        this.questions.addAll(questions);
    }

    private void validateDoesNotHaveDuplicateQuestion(final List<String> questionDescriptions) {
        Set<String> questionSet = new HashSet<>(questionDescriptions);
        if (questionSet.size() != questionDescriptions.size()) {
            throw new QuestionDescriptionDuplicatedException();
        }
    }
}
