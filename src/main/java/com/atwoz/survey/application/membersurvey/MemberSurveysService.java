package com.atwoz.survey.application.membersurvey;

import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.domain.survey.dto.SurveyComparisonRequest;
import com.atwoz.survey.exception.membersurvey.exceptions.RequiredSurveyNotSubmittedException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveySubmitDuplicateException;
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
public class MemberSurveysService {

    private final MemberSurveysRepository memberSurveysRepository;
    private final SurveyRepository surveyRepository;

    public void submitSurvey(final Long memberId, final List<SurveySubmitRequest> requests) {
        validateIsAllSubmittedRequiredSurveys(requests);

        MemberSurveys memberSurveys = memberSurveysRepository.findByMemberId(memberId)
                .orElseGet(() -> createNewMemberSurveysWithMemberId(memberId));
        validateEachSurveyRequestIsValid(requests);
        memberSurveys.submitSurveys(requests);
    }

    private void validateIsAllSubmittedRequiredSurveys(final List<SurveySubmitRequest> requests) {
        List<Long> requiredSurveyIds = surveyRepository.findAllRequiredSurveyIds();
        Set<Long> submittedSurveyIds = extractSubmittedSurveyIds(requests);

        if (!submittedSurveyIds.containsAll(requiredSurveyIds)) {
            throw new RequiredSurveyNotSubmittedException();
        }
    }

    private Set<Long> extractSubmittedSurveyIds(final List<SurveySubmitRequest> requests) {
        List<Long> submittedSurveyIds = requests.stream()
                .map(SurveySubmitRequest::surveyId)
                .toList();
        HashSet<Long> surveyIds = new HashSet<>(submittedSurveyIds);

        if (surveyIds.size() != submittedSurveyIds.size()) {
            throw new SurveySubmitDuplicateException();
        }

        return surveyIds;
    }

    private MemberSurveys createNewMemberSurveysWithMemberId(final Long memberId) {
        MemberSurveys memberSurveys = MemberSurveys.createWithMemberId(memberId);
        memberSurveysRepository.save(memberSurveys);
        return memberSurveys;
    }

    private void validateEachSurveyRequestIsValid(final List<SurveySubmitRequest> requests) {
        requests.forEach(request -> {
                    Survey survey = surveyRepository.findById(request.surveyId())
                            .orElseThrow(SurveyNotFoundException::new);
                    SurveyComparisonRequest comparisonRequest = SurveyComparisonRequest.from(request);
                    survey.validateIsValidSubmitSurveyRequest(comparisonRequest);
                });
    }
}
