package com.atwoz.survey.application.membersurvey;

import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberSurveysQueryService {

    private final MemberSurveysRepository memberSurveysRepository;

    public MemberSurveyResponse findMemberSurvey(final Long memberId, final Long surveyId) {
        return memberSurveysRepository.findMemberSurvey(memberId, surveyId);
    }

    public List<Long> findMatchMembers(final Long memberId) {
        return memberSurveysRepository.findMatchMembers(memberId);
    }
}
