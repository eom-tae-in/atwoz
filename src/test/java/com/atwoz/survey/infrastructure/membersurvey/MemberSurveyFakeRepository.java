package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurvey;
import com.atwoz.survey.domain.membersurvey.MemberSurveyRepository;

import java.util.HashMap;
import java.util.Map;

public class MemberSurveyFakeRepository implements MemberSurveyRepository {

    private final Map<Long, MemberSurvey> map = new HashMap<>();

    private Long id = 1L;

    @Override
    public MemberSurvey save(final MemberSurvey memberSurvey) {
        MemberSurvey newMemberSurvey = MemberSurvey.builder()
                .id(id++)
                .memberId(memberSurvey.getMemberId())
                .questionId(memberSurvey.getQuestionId())
                .answerNumber(memberSurvey.getAnswerNumber())
                .build();
        map.put(newMemberSurvey.getId(), newMemberSurvey);

        return newMemberSurvey;
    }

    @Override
    public boolean existsByMemberIdAndQuestionId(final Long memberId, final Long questionId) {
        return map.values()
                .stream()
                .anyMatch(memberSurvey -> isSameMemberSurvey(memberSurvey, memberId, questionId));
    }

    private boolean isSameMemberSurvey(final MemberSurvey memberSurvey, final Long memberId, final Long questionId) {
        return memberId.equals(memberSurvey.getMemberId()) && questionId.equals(memberSurvey.getQuestionId());
    }
}
