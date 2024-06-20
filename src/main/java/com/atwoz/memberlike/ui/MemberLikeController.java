package com.atwoz.memberlike.ui;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.memberlike.application.MemberLikeQueryService;
import com.atwoz.memberlike.application.MemberLikeService;
import com.atwoz.memberlike.application.dto.MemberLikeCreateRequest;
import com.atwoz.memberlike.infrastructure.dto.MemberLikePagingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/likes")
@RestController
public class MemberLikeController {

    private final MemberLikeService memberLikeService;
    private final MemberLikeQueryService memberLikeQueryService;

    @GetMapping("/send")
    public ResponseEntity<MemberLikePagingResponse> findSendLikesWithPaging(
            @AuthMember final Long memberId,
            @PageableDefault final Pageable pageable
            ) {
        return ResponseEntity.ok(memberLikeQueryService.findSendLikesWithPaging(memberId, pageable));
    }

    @GetMapping("/receive")
    public ResponseEntity<MemberLikePagingResponse> findReceivedLikesWithPaging(
            @AuthMember final Long memberId,
            @PageableDefault final Pageable pageable
    ) {
        return ResponseEntity.ok(memberLikeQueryService.findReceivedLikesWithPaging(memberId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> sendLike(@RequestBody @Valid final MemberLikeCreateRequest request,
                                         @AuthMember final Long memberId) {
        memberLikeService.sendLike(memberId, request);

        return ResponseEntity.ok()
                .build();
    }
}
