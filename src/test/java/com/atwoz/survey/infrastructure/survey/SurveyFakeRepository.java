package com.atwoz.survey.infrastructure.survey;

import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyAnswer;
import com.atwoz.survey.domain.survey.SurveyQuestion;
import com.atwoz.survey.domain.survey.SurveyRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SurveyFakeRepository implements SurveyRepository {

    private final Map<Long, Survey> map = new HashMap<>();

    private Long id = 1L;
    private Long surveyQuestionId = 1L;
    private Long surveyAnswerId = 1L;

    @Override
    public Survey save(final Survey survey) {
        Survey newSurvey = Survey.builder()
                .id(id++)
                .name(survey.getName())
                .required(survey.getRequired())
                .questions(injectSurveyQuestionIds(survey.getQuestions()))
                .build();
        map.put(newSurvey.getId(), newSurvey);

        return newSurvey;
    }

    private List<SurveyQuestion> injectSurveyQuestionIds(final List<SurveyQuestion> questions) {
        List<SurveyQuestion> copySurveyQuestions = new ArrayList<>();
        for (SurveyQuestion question : questions) {
            SurveyQuestion copySurveyQuestion = SurveyQuestion.builder()
                    .id(surveyQuestionId++)
                    .description(question.getDescription())
                    .answers(injectSurveyAnswersIds(question.getAnswers()))
                    .build();
            copySurveyQuestions.add(copySurveyQuestion);
        }
        return copySurveyQuestions;
    }

    private List<SurveyAnswer> injectSurveyAnswersIds(final List<SurveyAnswer> answers) {
        List<SurveyAnswer> copySurveyAnswers = new ArrayList<>();
        for (SurveyAnswer answer : answers) {
            SurveyAnswer copySurveyAnswer = SurveyAnswer.builder()
                    .id(surveyAnswerId++)
                    .number(answer.getNumber())
                    .description(answer.getDescription())
                    .build();
            copySurveyAnswers.add(copySurveyAnswer);
        }
        return copySurveyAnswers;
    }

    @Override
    public boolean isExistedByName(final String name) {
        return map.values()
                .stream()
                .anyMatch(survey -> survey.isSameName(name));
    }

    @Override
    public Optional<Survey> findById(final Long id) {
        return Optional.of(map.get(id));
    }

    @Override
    public List<Survey> findAllRequiredSurveys() {
        return map.values()
                .stream()
                .filter(Survey::isRequired)
                .toList();
    }
}
