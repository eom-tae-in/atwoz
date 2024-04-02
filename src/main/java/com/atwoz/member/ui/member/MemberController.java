package com.atwoz.member.ui.member;

import com.atwoz.member.application.member.MemberService;
import com.atwoz.member.application.member.dto.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.MemberNicknameRequest;
import com.atwoz.member.application.member.dto.MemberUpdateRequest;
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

    @GetMapping("/nickname/existence")
    public ResponseEntity<Void> checkMemberExists(@Valid @RequestBody final MemberNicknameRequest memberNicknameRequest) {
        memberService.checkMemberExists(memberNicknameRequest);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<Void> initializeMember(@PathVariable final Long memberId,
                                                 @Valid @RequestBody final MemberInitializeRequest memberInitializeRequest) {
        memberService.initializeMember(memberId, memberInitializeRequest);
        return ResponseEntity.status(CREATED)
                .build();
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@PathVariable final Long memberId,
                                             @Valid @RequestBody final MemberUpdateRequest memberUpdateRequest) {
        memberService.updateMember(memberId, memberUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable final Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent()
                .build();
    }
}
