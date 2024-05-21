package com.atwoz.survey.ui.survey;

import com.atwoz.survey.application.survey.SurveyService;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.ui.survey.dto.SurveyCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/surveys")
@RestController
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<SurveyCreateResponse> addSurvey(@RequestBody @Valid final SurveyCreateRequest request) {
        Survey survey = surveyService.addSurvey(request);
        return ResponseEntity.created(URI.create("/api/surveys/" + survey.getId()))
                .body(SurveyCreateResponse.from(survey));
    }
}
