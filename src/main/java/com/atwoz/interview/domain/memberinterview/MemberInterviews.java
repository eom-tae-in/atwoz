package com.atwoz.interview.domain.memberinterview;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.exception.exceptions.MemberInterviewNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class MemberInterviews extends BaseEntity {

    private static final String MEMBER_INTERVIEWS_COLUMN = "member_interviews_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @JoinColumn(name = MEMBER_INTERVIEWS_COLUMN)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberInterview> memberInterviews = new ArrayList<>();

    private MemberInterviews(final Long memberId) {
        this.memberId = memberId;
        this.memberInterviews = new ArrayList<>();
    }

    public static MemberInterviews createWithMemberId(final Long memberId) {
        return new MemberInterviews(memberId);
    }

    public void submitInterview(final Interview interview, final String answer) {
        MemberInterview memberInterview = MemberInterview.createDefault(interview, answer);
        this.memberInterviews.add(memberInterview);
    }

    public void updateInterviewAnswer(final Long interviewId, final String answer) {
        MemberInterview memberInterview = findMemberInterview(interviewId);
        memberInterview.updateAnswer(answer);
    }

    public MemberInterview findMemberInterview(final Long interviewId) {
        return this.memberInterviews.stream()
                .filter(memberInterview -> memberInterview.isSameInterview(interviewId))
                .findAny()
                .orElseThrow(MemberInterviewNotFoundException::new);
    }
}
