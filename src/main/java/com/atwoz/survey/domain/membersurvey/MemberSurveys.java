package com.atwoz.survey.domain.membersurvey;

import com.atwoz.survey.application.membersurvey.dto.SurveyQuestionSubmitRequest;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberSurveys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @JoinColumn(name = "member_surveys_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberSurvey> memberSurveys = new ArrayList<>();

    public static MemberSurveys createWithMemberId(final Long memberId) {
        return MemberSurveys.builder()
                .memberId(memberId)
                .memberSurveys(new ArrayList<>())
                .build();
    }

    public void submitSurveys(final List<SurveySubmitRequest> requests) {
        // 각 request에는 id와 questions이 있다.
        List<MemberSurvey> memberSurveys = new ArrayList<>();
        requests.forEach(request -> {
                    List<MemberSurvey> convertedMemberSurveys = convertMemberSurveys(request.surveyId(), request.questions());
                    memberSurveys.addAll(convertedMemberSurveys);
                });
        this.memberSurveys.addAll(memberSurveys);
    }

    private List<MemberSurvey> convertMemberSurveys(final Long surveyId, final List<SurveyQuestionSubmitRequest> questions) {
        return questions.stream()
                .map(question -> MemberSurvey.of(surveyId, question.questionId(), question.answerNumber()))
                .toList();
    }
}
