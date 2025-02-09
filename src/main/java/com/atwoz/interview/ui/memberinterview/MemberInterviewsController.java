package com.atwoz.interview.ui.memberinterview;

import com.atwoz.interview.application.memberinterview.MemberInterviewsQueryService;
import com.atwoz.interview.application.memberinterview.MemberInterviewsService;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewUpdateRequest;
import com.atwoz.interview.domain.memberinterview.MemberInterview;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewUpdateResponse;
import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewsResponse;
import com.atwoz.member.ui.auth.support.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/interviews")
@RestController
public class MemberInterviewsController {

    private static final String INTERVIEW_PARAM_VALUE = "type";
    private static final String INTERVIEW_DEFAULT_TYPE = "나";

    private final MemberInterviewsService memberInterviewsService;
    private final MemberInterviewsQueryService memberInterviewsQueryService;

    @PostMapping("/{interviewId}")
    public ResponseEntity<Void> submitInterview(
            @PathVariable final Long interviewId,
            @AuthMember final Long memberId,
            @RequestBody @Valid final MemberInterviewSubmitRequest request
    ) {
        memberInterviewsService.submitInterview(interviewId, memberId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{interviewId}")
    public ResponseEntity<MemberInterviewUpdateResponse> updateMemberInterviewAnswer(
            @PathVariable final Long interviewId,
            @AuthMember final Long memberId,
            @RequestBody @Valid final MemberInterviewUpdateRequest request
    ) {
        MemberInterview memberInterview = memberInterviewsService.updateMemberInterviewAnswer(interviewId, memberId, request);
        return ResponseEntity.ok()
                .body(new MemberInterviewUpdateResponse(interviewId, memberInterview.getAnswer()));
    }

    @GetMapping("/{interviewId}")
    public ResponseEntity<MemberInterviewDetailResponse> findMemberInterviewAnswer(
            @PathVariable final Long interviewId,
            @AuthMember final Long memberId
    ) {
        return ResponseEntity.ok()
                .body(memberInterviewsQueryService.findMemberInterviewAnswer(interviewId, memberId));
    }

    @GetMapping
    public ResponseEntity<MemberInterviewsResponse> findMemberInterviewsByType(
            @AuthMember final Long memberId,
            @RequestParam(value = INTERVIEW_PARAM_VALUE, defaultValue = INTERVIEW_DEFAULT_TYPE) final String type
    ) {
        List<MemberInterviewSimpleResponse> interviews = memberInterviewsQueryService.findMemberInterviewsByType(memberId, type);
        return ResponseEntity.ok()
                .body(new MemberInterviewsResponse(interviews));
    }
}
