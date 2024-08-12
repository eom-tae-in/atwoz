package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberSurveysRepositoryImpl implements MemberSurveysRepository {

    private final MemberSurveysJpaRepository memberSurveysJpaRepository;
    private final MemberSurveysQueryRepository memberSurveysQueryRepository;

    @Override
    public MemberSurveys save(final MemberSurveys memberSurveys) {
        return memberSurveysJpaRepository.save(memberSurveys);
    }

    @Override
    public Optional<MemberSurveys> findByMemberId(final Long memberId) {
        return memberSurveysJpaRepository.findByMemberId(memberId);
    }

    @Override
    public List<SurveySoulmateResponse> findSoulmates(final Long memberId) {
        return memberSurveysQueryRepository.findSoulmates(memberId);
    }

    @Override
    public Optional<MemberSurveyResponse> findMemberSurvey(final Long memberId, final Long surveyId) {
        return memberSurveysQueryRepository.findMemberSurvey(memberId, surveyId);
    }
}
