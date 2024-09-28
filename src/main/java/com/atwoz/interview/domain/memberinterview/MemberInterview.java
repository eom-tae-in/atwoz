package com.atwoz.interview.domain.memberinterview;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberInterview extends BaseEntity {

    private static final boolean SUBMIT_STATUS = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interview interview;

    private String answer;

    private boolean isSubmitted;

    private MemberInterview(final Interview interview, final String answer) {
        this.interview = interview;
        this.answer = answer;
        this.isSubmitted = SUBMIT_STATUS;
    }

    public static MemberInterview createDefault(final Interview interview, final String answer) {
        return new MemberInterview(interview, answer);
    }

    public void updateAnswer(final String answer) {
        this.answer = answer;
    }

    public boolean isSameInterview(final Long interviewId) {
        return this.interview.isSameId(interviewId);
    }

    public boolean isSameType(final InterviewType interviewType) {
        return this.interview.isSameType(interviewType);
    }

    public Long getInterviewId() {
        return interview.getId();
    }
}
