package com.atwoz.survey.ui.membersurvey;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.survey.application.membersurvey.MemberSurveyService;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/surveys")
@RestController
public class MemberSurveyController {

    private final MemberSurveyService memberSurveyService;

    @PostMapping("/submit")
    public ResponseEntity<Void> submitSurvey(@AuthMember final Long memberId,
                                             @RequestBody @Valid final List<SurveySubmitRequest> request) {
        memberSurveyService.submitSurvey(memberId, request);
        return ResponseEntity.ok()
                .build();
    }
}
