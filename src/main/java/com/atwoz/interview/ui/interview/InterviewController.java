package com.atwoz.interview.ui.interview;

import com.atwoz.interview.application.interview.InterviewService;
import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.ui.interview.dto.InterviewCreateResponse;
import com.atwoz.interview.ui.interview.dto.InterviewUpdateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping
    public ResponseEntity<InterviewCreateResponse> createInterview(@RequestBody @Valid final InterviewCreateRequest request) {
        Interview interview = interviewService.createInterview(request);
        return ResponseEntity.created(URI.create("/api/interviews/" + interview.getId()))
                .body(InterviewCreateResponse.of(interview.getQuestion(), interview.getInterviewType()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<InterviewUpdateResponse> updateInterview(
            @PathVariable final Long id,
            @RequestBody @Valid final InterviewUpdateRequest request) {
        Interview updatedInterview = interviewService.updateInterview(id, request);
        return ResponseEntity.ok()
                .body(InterviewUpdateResponse.from(updatedInterview));
    }
}
