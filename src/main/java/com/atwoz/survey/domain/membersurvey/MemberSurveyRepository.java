package com.atwoz.survey.domain.membersurvey;

public interface MemberSurveyRepository {

    MemberSurvey save(MemberSurvey memberSurvey);
    boolean existsByMemberIdAndQuestionId(Long memberId, Long questionId);
}
