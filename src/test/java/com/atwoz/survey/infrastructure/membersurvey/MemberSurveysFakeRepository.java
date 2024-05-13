package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberSurveysFakeRepository implements MemberSurveysRepository {

    private final Map<Long, MemberSurveys> map = new HashMap<>();

    private Long id = 1L;

    @Override
    public MemberSurveys save(final MemberSurveys memberSurveys) {
        MemberSurveys newMemberSurveys = MemberSurveys.builder()
                .id(id++)
                .memberId(memberSurveys.getMemberId())
                .memberSurveys(memberSurveys.getMemberSurveys())
                .build();

        map.put(id, newMemberSurveys);

        return newMemberSurveys;
    }

    @Override
    public Optional<MemberSurveys> findByMemberId(final Long memberId) {
        return Optional.ofNullable(map.get(memberId));
    }
}
