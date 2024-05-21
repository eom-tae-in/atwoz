package com.atwoz.survey.ui.membersurvey;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.survey.application.membersurvey.MemberSurveysQueryService;
import com.atwoz.survey.application.membersurvey.MemberSurveysService;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;
import com.atwoz.survey.ui.membersurvey.dto.MatchMemberSearchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/surveys")
@RestController
public class MemberSurveysController {

    private final MemberSurveysService memberSurveysService;
    private final MemberSurveysQueryService memberSurveysQueryService;

    @PostMapping
    public ResponseEntity<Void> submitSurveys(@AuthMember final Long memberId,
                                              @RequestBody @Valid final List<SurveySubmitRequest> requests) {
        memberSurveysService.submitSurvey(memberId, requests);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<MemberSurveyResponse> findMemberSurvey(@AuthMember final Long memberId,
                                                                 @PathVariable final Long surveyId) {
        return ResponseEntity.ok()
                .body(memberSurveysQueryService.findMemberSurvey(memberId, surveyId));
    }

    @GetMapping("/match")
    public ResponseEntity<MatchMemberSearchResponse> findMatchMembers(@AuthMember final Long memberId) {
        List<Long> members = memberSurveysQueryService.findMatchMembers(memberId);

        return ResponseEntity.ok()
                .body(new MatchMemberSearchResponse(members));
    }
}
