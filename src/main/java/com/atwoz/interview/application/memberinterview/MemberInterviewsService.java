package com.atwoz.interview.application.memberinterview;

import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.exception.exceptions.InterviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberInterviewsService {

    private final InterviewRepository interviewRepository;
    private final MemberInterviewsRepository memberInterviewsRepository;

    public void submitInterview(final Long interviewId, final Long memberId, final MemberInterviewSubmitRequest request) {
        Interview targetInterview = findInterviewById(interviewId);
        MemberInterviews memberInterviews = memberInterviewsRepository.findByMemberId(memberId)
                .orElseGet(() -> createNewMemberInterviewsWithMemberId(memberId));
        memberInterviews.submitInterview(targetInterview, request.answer());
    }

    private Interview findInterviewById(final Long interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(InterviewNotFoundException::new);
    }

    private MemberInterviews createNewMemberInterviewsWithMemberId(final Long memberId) {
        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
        return memberInterviewsRepository.save(memberInterviews);
    }
}
