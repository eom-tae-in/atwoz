package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.survey.domain.membersurvey.MemberSurvey;
import com.atwoz.survey.domain.membersurvey.MemberSurveys;
import com.atwoz.survey.domain.membersurvey.MemberSurveysRepository;
import com.atwoz.survey.exception.membersurvey.exceptions.MemberSurveysNotFoundException;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static com.atwoz.survey.fixture.SurveySoulmateResponseFixture.소울메이트_응답_회원;

public class MemberSurveysFakeRepository implements MemberSurveysRepository {

    private static final int MINIMUM_MATCH_SIZE = 30;

    private final Map<Long, MemberSurveys> map = new HashMap<>();

    private Long id = 1L;

    private final MemberRepository memberRepository;

    public MemberSurveysFakeRepository(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberSurveysFakeRepository() {
        this.memberRepository = new MemberFakeRepository();
    }

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
    public List<SurveySoulmateResponse> findSoulmates(final Long memberId) {
        Member member = findMemberById(memberId);
        Gender memberGender = extractMemberGender(member);

        List<SurveySoulmateResponse> result = new ArrayList<>();
        MemberSurveys memberSurveys = getMemberSurveys(memberId);
        List<MemberSurveys> otherMembers = map.values()
                .stream()
                .filter(otherMemberSurveys -> !memberId.equals(otherMemberSurveys.getMemberId()))
                .filter(otherMemberSurveys -> {
                    Member otherMember = findMemberById(otherMemberSurveys.getMemberId());
                    Gender otherGender = extractMemberGender(otherMember);
                    return otherGender != memberGender;
                })
                .toList();

        for (MemberSurveys otherMemberSurveys : otherMembers) {
            if (isSameAnswer(memberSurveys, otherMemberSurveys)) {
                Member otherMember = findMemberById(otherMemberSurveys.getMemberId());
                SurveySoulmateResponse response = 소울메이트_응답_회원(otherMember);
                result.add(response);
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

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    private Gender extractMemberGender(final Member member) {
        MemberProfile memberProfile = member.getMemberProfile();
        return memberProfile.getProfile()
                .getPhysicalProfile()
                .getGender();
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
