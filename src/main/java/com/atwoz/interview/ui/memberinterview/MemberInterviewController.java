package com.atwoz.interview.ui.memberinterview;

import com.atwoz.interview.application.memberinterview.MemberInterviewsService;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import com.atwoz.member.ui.auth.support.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/interviews")
@RestController
public class MemberInterviewController {

    private final MemberInterviewsService memberInterviewsService;

    @PostMapping("/{interviewId}")
    public ResponseEntity<Void> submitInterview(@PathVariable final Long interviewId,
                                                @AuthMember final Long memberId,
                                                @RequestBody @Valid final MemberInterviewSubmitRequest request) {
        memberInterviewsService.submitInterview(interviewId, memberId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
