package com.atwoz.survey.domain.membersurvey;

import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;

import java.util.List;
import java.util.Optional;

public interface MemberSurveysRepository {

    MemberSurveys save(MemberSurveys memberSurveys);
    Optional<MemberSurveys> findByMemberId(Long memberId);
    List<Long> findMatchMembers(Long memberId);
    MemberSurveyResponse findMemberSurvey(Long memberId, Long surveyId);
}
