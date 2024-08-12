package com.atwoz.survey.application.membersurvey;

import com.atwoz.survey.application.membersurvey.soulmatepolicy.SoulmatePolicy;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.exception.membersurvey.exceptions.MemberSurveysNotFoundException;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberSurveysQueryService {

    private final MemberSurveysRepository memberSurveysRepository;
    private final SoulmatePolicy soulmatePolicy;

    public MemberSurveyResponse findMemberSurvey(final Long memberId, final Long surveyId) {
        return memberSurveysRepository.findMemberSurvey(memberId, surveyId)
                .orElseThrow(MemberSurveysNotFoundException::new);
    }

    public List<SurveySoulmateResponse> findSoulmates(final Long memberId) {
        List<SurveySoulmateResponse> soulmates = memberSurveysRepository.findSoulmates(memberId);
        return soulmatePolicy.extractSoulmatesWithPolicy(soulmates);
    }
}
