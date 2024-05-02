package com.atwoz.member.ui.member;

import com.atwoz.member.application.member.MemberQueryService;
import com.atwoz.member.application.member.MemberService;
import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.ui.auth.support.auth.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @GetMapping("/{nickname}/existence")
    public ResponseEntity<Void> checkMemberExists(@PathVariable final String nickname) {
        memberQueryService.checkMemberExists(nickname);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/me")
    public ResponseEntity<Void> initializeMember(@AuthMember final Long memberId,
                                                 @Valid @RequestBody final MemberInitializeRequest memberInitializeRequest) {
        memberService.initializeMember(memberId, memberInitializeRequest);
        return ResponseEntity.status(CREATED)
                .build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable final Long memberId) {
        return ResponseEntity.ok(memberQueryService.findMember(memberId));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMember(@AuthMember final Long memberId,
                                             @Valid @RequestBody final MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMember(@AuthMember final Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
