package com.atwoz.interview.ui.interview;

import com.atwoz.interview.application.interview.InterviewService;
import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.ui.interview.dto.InterviewCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/interviews")
@RestController
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping()
    public ResponseEntity<InterviewCreateResponse> createInterview(@RequestBody @Valid final InterviewCreateRequest request) {
        Interview interview = interviewService.createInterview(request);
        return ResponseEntity.created(URI.create("/api/interviews/" + interview.getId()))
                .body(InterviewCreateResponse.of(interview.getQuestion(), interview.getInterviewType()));
    }
}
