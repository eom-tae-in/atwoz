package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurvey;
import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.exception.membersurvey.exceptions.MemberSurveysNotFoundException;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemberSurveysFakeRepository implements MemberSurveysRepository {

    private static final int MINIMUM_MATCH_SIZE = 30;

    private final Map<Long, MemberSurveys> map = new HashMap<>();

    private Long id = 1L;

    @Override
    public MemberSurveys save(final MemberSurveys memberSurveys) {
        MemberSurveys newMemberSurveys = MemberSurveys.builder()
                .id(id)
                .memberId(memberSurveys.getMemberId())
                .memberSurveys(memberSurveys.getMemberSurveys())
                .build();

        map.put(id++, newMemberSurveys);

        return newMemberSurveys;
    }

    @Override
    public Optional<MemberSurveys> findByMemberId(final Long memberId) {
        return map.values()
                .stream()
                .filter(memberSurveys -> memberId.equals(memberSurveys.getMemberId()))
                .findAny();
    }

    @Override
    public List<Long> findMatchMembers(final Long memberId) {
        List<Long> result = new ArrayList<>();
        MemberSurveys memberSurveys = getMemberSurveys(memberId);
        List<MemberSurveys> otherMembers = map.values()
                .stream()
                .filter(otherMemberSurveys -> !memberId.equals(otherMemberSurveys.getMemberId()))
                .toList();

        for (MemberSurveys otherMemberSurveys : otherMembers) {
            if (isSameAnswer(memberSurveys, otherMemberSurveys)) {
                result.add(otherMemberSurveys.getMemberId());
            }
        }

        return result;
    }

    private boolean isSameAnswer(final MemberSurveys memberSurveys, final MemberSurveys otherMemberSurveys) {
        int same = 0;
        for (MemberSurvey memberSurvey : memberSurveys.getMemberSurveys()) {
            for (MemberSurvey otherSurvey : otherMemberSurveys.getMemberSurveys()) {
                if (memberSurvey.getSurveyId().equals(otherSurvey.getSurveyId()) &&
                    memberSurvey.getQuestionId().equals(otherSurvey.getQuestionId()) &&
                    memberSurvey.getAnswerNumber().equals(otherSurvey.getAnswerNumber())) {
                    same++;
                }
            }
        }
        return same >= MINIMUM_MATCH_SIZE;
    }

    @Override
    public Optional<MemberSurveyResponse> findMemberSurvey(final Long memberId, final Long surveyId) {
        MemberSurveys memberSurveys = getMemberSurveys(memberId);
        List<MemberSurveyQuestionResponse> questions = new ArrayList<>();
        memberSurveys.getMemberSurveys()
                .stream()
                .filter(memberSurvey -> surveyId.equals(memberSurvey.getSurveyId()))
                .forEach(memberSurvey -> questions.add(
                        new MemberSurveyQuestionResponse(memberSurvey.getQuestionId(), memberSurvey.getAnswerNumber())
                ));

        return Optional.of(new MemberSurveyResponse(surveyId, questions));
    }

    private MemberSurveys getMemberSurveys(final Long memberId) {
        return map.values()
                .stream()
                .filter(memberSurveys -> memberId.equals(memberSurveys.getMemberId()))
                .findAny()
                .orElseThrow(MemberSurveysNotFoundException::new);
    }
}
