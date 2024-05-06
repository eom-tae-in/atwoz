package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSurveyJpaRepository extends JpaRepository<MemberSurvey, Long> {

    MemberSurvey save(MemberSurvey memberSurvey);
    boolean existsByMemberIdAndQuestionId(Long memberId, Long questionId);
}
