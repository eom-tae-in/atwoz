package com.atwoz.survey.application.membersurvey;

import com.atwoz.survey.application.membersurvey.dto.SurveyQuestionSubmitRequest;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.domain.membersurvey.MemberSurvey;
import com.atwoz.survey.domain.membersurvey.MemberSurveyRepository;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyQuestion;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.domain.survey.dto.SurveyComparisonRequest;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAlreadySubmittedException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionNotSubmittedException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionSubmitDuplicateException;
import com.atwoz.survey.exception.survey.exceptions.SurveyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberSurveyService {

    private final MemberSurveyRepository memberSurveyRepository;
    private final SurveyRepository surveyRepository;

    public void submitSurvey(final Long memberId, final List<SurveySubmitRequest> requests) {
        validateIsContainsAllRequiredSurveys(requests);
        requests.forEach(request -> submitEachSurvey(memberId, request));
    }

    private void validateIsContainsAllRequiredSurveys(final List<SurveySubmitRequest> requests) {
        List<Long> requiredSurveyIds = surveyRepository.findAllRequiredSurveys()
                .stream()
                .map(Survey::getId)
                .toList();
        List<Long> submittedSurveyIds = requests.stream()
                .map(SurveySubmitRequest::surveyId)
                .toList();
        validateIsNotDuplicateSurveyIds(submittedSurveyIds);
        if (!submittedSurveyIds.containsAll(requiredSurveyIds)) {
            throw new SurveyNotFoundException();
        }
    }

    private void validateIsNotDuplicateSurveyIds(final List<Long> surveyIds) {
        Set<Long> set = new HashSet<>(surveyIds);
        if (surveyIds.size() != set.size()) {
            throw new SurveyQuestionSubmitDuplicateException();
        }
    }

    private void submitEachSurvey(final Long memberId, final SurveySubmitRequest request) {
        Survey survey = findSurveyById(request.surveyId());
        survey.validateIsAllContainsSubmitQuestions(SurveyComparisonRequest.from(request));

        survey.getQuestions()
                .forEach(question -> saveQuestionAnswer(memberId, request, question));
    }

    private Survey findSurveyById(final Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
    }

    private void saveQuestionAnswer(final Long memberId, final SurveySubmitRequest request, final SurveyQuestion question) {
        validateIsAlreadySubmittedQuestion(memberId, question.getId());
        SurveyQuestionSubmitRequest questionRequest = findQuestionSubmitRequest(question.getId(), request);
        MemberSurvey memberSurvey = MemberSurvey.of(memberId, question.getId(), questionRequest.answerId());
        memberSurveyRepository.save(memberSurvey);
    }

    private void validateIsAlreadySubmittedQuestion(final Long memberId, final Long questionId) {
        if (memberSurveyRepository.existsByMemberIdAndQuestionId(memberId, questionId)) {
            throw new SurveyAlreadySubmittedException();
        }
    }

    private SurveyQuestionSubmitRequest findQuestionSubmitRequest(final Long questionId, final SurveySubmitRequest request) {
        return request.questions()
                .stream()
                .filter(question -> questionId.equals(question.questionId()))
                .findAny()
                .orElseThrow(SurveyQuestionNotSubmittedException::new);
    }
}
