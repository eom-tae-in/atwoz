package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurvey;
import com.atwoz.survey.domain.membersurvey.MemberSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberSurveyRepositoryImpl implements MemberSurveyRepository {

    private final MemberSurveyJpaRepository memberSurveyJpaRepository;

    @Override
    public MemberSurvey save(final MemberSurvey memberSurvey) {
        return memberSurveyJpaRepository.save(memberSurvey);
    }

    @Override
    public boolean existsByMemberIdAndQuestionId(final Long memberId, final Long questionId) {
        return memberSurveyJpaRepository.existsByMemberIdAndQuestionId(memberId, questionId);
    }
}
