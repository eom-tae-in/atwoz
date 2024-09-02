package com.atwoz.interview.domain.interview;

import com.atwoz.interview.domain.interview.vo.InterviewType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Interview {

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
}
