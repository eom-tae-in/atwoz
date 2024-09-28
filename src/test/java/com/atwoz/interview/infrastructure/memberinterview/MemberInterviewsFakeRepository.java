package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.domain.memberinterview.MemberInterview;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.exception.exceptions.InterviewNotFoundException;
import com.atwoz.interview.exception.exceptions.MemberInterviewNotSubmittedException;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberInterviewsFakeRepository implements MemberInterviewsRepository {

    private static final String EMPTY_ANSWER = "";

    private final Map<Long, MemberInterviews> map = new HashMap<>();

    private Long id = 0L;

    private InterviewRepository interviewRepository;

    public MemberInterviewsFakeRepository(final InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public MemberInterviews save(final MemberInterviews memberInterviews) {
        id++;
        MemberInterviews savedMemberInterviews = MemberInterviews.builder()
                .id(id)
                .memberId(memberInterviews.getMemberId())
                .memberInterviews(memberInterviews.getMemberInterviews())
                .build();
        map.put(id, savedMemberInterviews);

        return savedMemberInterviews;
    }

    @Override
    public Optional<MemberInterviews> findByMemberId(final Long memberId) {
        return map.values()
                .stream()
                .filter(memberInterviews -> memberId.equals(memberInterviews.getMemberId()))
                .findAny();
    }

    @Override
    public MemberInterviewDetailResponse findMemberInterviewAnswer(final Long interviewId, final Long memberId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(InterviewNotFoundException::new);
        String answer = map.values()
                .stream()
                .filter(interviews -> memberId.equals(interviews.getMemberId()))
                .findAny()
                .map(memberInterviews -> memberInterviews.findMemberInterview(interviewId))
                .map(MemberInterview::getAnswer)
                .orElse(EMPTY_ANSWER);

        return new MemberInterviewDetailResponse(interviewId, interview.getQuestion(), answer);
    }

    @Override
    public List<MemberInterviewSimpleResponse> findMemberInterviewsByType(final Long memberId, final String type) {
        InterviewType interviewType = InterviewType.findByName(type);
        return map.values().stream()
                .filter(memberInterviews -> memberId.equals(memberInterviews.getMemberId()))
                .findAny()
                .orElseThrow(MemberInterviewNotSubmittedException::new)
                .getMemberInterviews()
                .stream()
                .filter(memberInterview -> memberInterview.isSameType(interviewType))
                .map(memberInterview -> new MemberInterviewSimpleResponse(
                        memberInterview.getInterviewId(),
                        memberInterview.getInterview().getQuestion(),
                        memberInterview.isSubmitted()))
                .toList();
    }
}
