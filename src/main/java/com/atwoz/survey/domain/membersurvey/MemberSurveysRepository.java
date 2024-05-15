package com.atwoz.survey.domain.membersurvey;

import java.util.List;
import java.util.Optional;

public interface MemberSurveysRepository {

    MemberSurveys save(MemberSurveys memberSurveys);
    Optional<MemberSurveys> findByMemberId(Long memberId);
    List<Long> findMatchMembers(Long memberId);
}
