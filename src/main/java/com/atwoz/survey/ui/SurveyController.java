package com.atwoz.survey.ui;

import com.atwoz.survey.application.SurveyService;
import com.atwoz.survey.application.dto.SurveyCreateRequest;
import com.atwoz.survey.ui.dto.SurveyResponse;
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
    public ResponseEntity<SurveyResponse> addSurvey(@RequestBody @Valid final SurveyCreateRequest request) {
        Long id = surveyService.addSurvey(request);
        return ResponseEntity.created(URI.create("/api/surveys/" + id))
                .build();
    }
}
