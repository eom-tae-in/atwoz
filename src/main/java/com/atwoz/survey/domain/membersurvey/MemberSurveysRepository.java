package com.atwoz.survey.domain.membersurvey;

import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

import java.util.List;
import java.util.Optional;

public interface MemberSurveysRepository {

    MemberSurveys save(MemberSurveys memberSurveys);
    Optional<MemberSurveys> findByMemberId(Long memberId);
    List<SurveySoulmateResponse> findSoulmates(Long memberId);
    Optional<MemberSurveyResponse> findMemberSurvey(Long memberId, Long surveyId);
}
