package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSurveysJpaRepository extends JpaRepository<MemberSurveys, Long> {

    MemberSurveys save(MemberSurveys memberSurveys);
    Optional<MemberSurveys> findByMemberId(Long memberId);
}
