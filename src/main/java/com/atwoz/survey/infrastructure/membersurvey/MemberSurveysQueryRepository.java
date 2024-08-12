package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyAnswerResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.member.domain.member.QMemberProfile.memberProfile;
import static com.atwoz.member.domain.member.profile.QProfile.profile;
import static com.atwoz.member.domain.member.profile.physical.QPhysicalProfile.physicalProfile;
import static com.atwoz.member.domain.member.profile.vo.QLocation.location;

import static com.atwoz.survey.domain.membersurvey.QMemberSurvey.memberSurvey;
import static com.atwoz.survey.domain.membersurvey.QMemberSurveys.memberSurveys1;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class MemberSurveysQueryRepository {

    private static final int MINIMUM_MATCH_SIZE = 30;

    private final JPAQueryFactory jpaQueryFactory;

    public List<SurveySoulmateResponse> findSoulmates(final Long memberId) {
        List<MemberSurveyAnswerResponse> memberSurveyAnswerResponse = convertMemberSurveys(memberId);
        List<SurveySoulmateResponse> result = new ArrayList<>();

        List<Long> otherMembers = collectMembersExceptCurrentMember(memberId);
        otherMembers.forEach(id -> {
            List<MemberSurveyAnswerResponse> otherMemberSurveyAnswerResponse = convertMemberSurveys(id);
            if (isMatched(memberSurveyAnswerResponse, otherMemberSurveyAnswerResponse)) {
                SurveySoulmateResponse response = convertSoulmateResponse(id);
                result.add(response);
            }
        });
        return result;
    }

    private List<MemberSurveyAnswerResponse> convertMemberSurveys(final Long memberId) {
        return jpaQueryFactory.select(constructor(MemberSurveyAnswerResponse.class,
                memberSurveys1.memberId,
                memberSurvey.surveyId,
                memberSurvey.questionId,
                memberSurvey.answerNumber)
        ).from(memberSurveys1)
                .join(memberSurveys1.memberSurveys, memberSurvey)
                .where(memberSurveys1.memberId.eq(memberId))
                .fetch();
    }

    private List<Long> collectMembersExceptCurrentMember(final Long memberId) {
        Gender memberGender = extractMemberGender(memberId);
        List<Long> otherMembers = jpaQueryFactory.selectDistinct(memberSurveys1.memberId)
                .from(memberSurveys1)
                .where(memberSurveys1.memberId.ne(memberId))
                .fetch();

        return otherMembers.stream()
                .filter(id -> !extractMemberGender(id).equals(memberGender))
                .toList();
    }

    private Gender extractMemberGender(final Long memberId) {
        return jpaQueryFactory.select(physicalProfile.gender)
                .from(member)
                .join(member.memberProfile, memberProfile)
                .join(memberProfile.profile, profile)
                .join(profile.physicalProfile, physicalProfile)
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    private boolean isMatched(final List<MemberSurveyAnswerResponse> memberResponse, final List<MemberSurveyAnswerResponse> otherResponse) {
        int matchedSurveyAnswer = 0;
        for (MemberSurveyAnswerResponse member : memberResponse) {
            for (MemberSurveyAnswerResponse other : otherResponse) {
                if (isSameAnswer(member, other)) {
                    matchedSurveyAnswer++;
                }
            }
        }
        return matchedSurveyAnswer >= MINIMUM_MATCH_SIZE;
    }

    private boolean isSameAnswer(final MemberSurveyAnswerResponse member, final MemberSurveyAnswerResponse other) {
        return member.surveyId().equals(other.surveyId()) &&
                member.questionId().equals(other.questionId()) &&
                member.answerNumber().equals(other.answerNumber());
    }

    private SurveySoulmateResponse convertSoulmateResponse(final Long id) {
        return jpaQueryFactory.select(constructor(SurveySoulmateResponse.class,
                        member.id,
                        member.nickname,
                        location.city,
                        location.sector,
                        physicalProfile.age
                ))
                .from(member)
                .join(member.memberProfile, memberProfile)
                .join(memberProfile.profile, profile)
                .join(profile.physicalProfile, physicalProfile)
                .where(member.id.eq(id))
                .fetchOne();
    }

    public Optional<MemberSurveyResponse> findMemberSurvey(final Long memberId, final Long surveyId) {
        List<MemberSurveyQuestionResponse> questions = jpaQueryFactory.select(
                        constructor(MemberSurveyQuestionResponse.class,
                                memberSurvey.questionId,
                                memberSurvey.answerNumber)
                )
                .from(memberSurveys1)
                .join(memberSurveys1.memberSurveys, memberSurvey)
                .where(
                        memberSurveys1.memberId.eq(memberId)
                                .and(memberSurvey.surveyId.eq(memberId))
                )
                .fetch();
        return Optional.of(new MemberSurveyResponse(surveyId, questions));
    }
}
