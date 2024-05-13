package com.atwoz.survey.ui.membersurvey;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.survey.application.membersurvey.MemberSurveysService;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/submit")
    public ResponseEntity<Void> submitSurveys(@AuthMember final Long memberId,
                                              @RequestBody @Valid final List<SurveySubmitRequest> requests) {
        memberSurveysService.submitSurvey(memberId, requests);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
