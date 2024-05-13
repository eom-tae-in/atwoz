package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberSurveysRepositoryImpl implements MemberSurveysRepository {

    private final MemberSurveysJpaRepository memberSurveysJpaRepository;

    @Override
    public MemberSurveys save(final MemberSurveys memberSurveys) {
        return memberSurveysJpaRepository.save(memberSurveys);
    }

    @Override
    public Optional<MemberSurveys> findByMemberId(final Long memberId) {
        return memberSurveysJpaRepository.findByMemberId(memberId);
    }
}
