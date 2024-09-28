package com.atwoz.interview.domain.interview;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class Interview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String question;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    private Interview(final String question, final InterviewType interviewType) {
        this.question = question;
        this.interviewType = interviewType;
    }

    public static Interview createWith(final String question, final String type) {
        InterviewType interviewType = InterviewType.findByName(type);
        return new Interview(question, interviewType);
    }

    public void updateInterview(final String question, final String type) {
        this.question = question;
        this.interviewType = InterviewType.findByName(type);
    }

    public boolean isSameId(final Long id) {
        return id.equals(this.id);
    }

    public boolean isSameType(final InterviewType interviewType) {
        return interviewType.equals(this.interviewType);
    }
}
