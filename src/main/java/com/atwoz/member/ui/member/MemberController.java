package com.atwoz.member.ui.member;

import com.atwoz.member.application.member.MemberQueryService;
import com.atwoz.member.application.member.MemberService;
import com.atwoz.member.application.member.dto.MemberAccountStatusPatchRequest;
import com.atwoz.member.application.member.dto.MemberContactInfoDuplicationCheckResponse;
import com.atwoz.member.application.member.dto.MemberContactInfoPatchRequest;
import com.atwoz.member.application.member.dto.MemberNotificationsPatchRequest;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import com.atwoz.member.ui.auth.support.AuthMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    // TODO: 회원 조회 기능
    // TODO: 회원 정보 변경 기능

    @GetMapping("/me/notifications")
    public ResponseEntity<MemberNotificationsResponse> findMemberNotifications(@AuthMember final Long memberId) {
        return ResponseEntity.ok(memberQueryService.findMemberNotifications(memberId));
    }

    @GetMapping("/me/statuses/account")
    public ResponseEntity<MemberAccountStatusResponse> findMemberAccountStatus(@AuthMember final Long memberId) {
        return ResponseEntity.ok(memberQueryService.findMemberAccountStatus(memberId));
    }

    @GetMapping("/me/contact-info")
    public ResponseEntity<MemberContactInfoResponse> findMemberContactInfo(@AuthMember final Long memberId) {
        return ResponseEntity.ok(memberQueryService.findMemberContactInfo(memberId));
    }

    @GetMapping("/{contactType}/{contactValue}/duplication")
    public ResponseEntity<MemberContactInfoDuplicationCheckResponse> checkContactInfoDuplicated(
            @PathVariable final String contactType,
            @PathVariable final String contactValue
    ) {
        return ResponseEntity.ok(memberQueryService.checkContactInfoDuplicated(contactType, contactValue));
    }

    @PatchMapping("/me/notifications")
    public ResponseEntity<Void> patchMemberNotifications(
            @AuthMember final Long memberId,
            @RequestBody @Valid final MemberNotificationsPatchRequest request
    ) {
        memberService.patchMemberNotifications(memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/me/statuses/account")
    public ResponseEntity<Void> patchMemberAccountStatus(
            @AuthMember final Long memberId,
            @RequestBody @Valid final MemberAccountStatusPatchRequest request
    ) {
        memberService.patchMemberAccountStatus(memberId, request);
        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/me/contact-info")
    public ResponseEntity<Void> patchMemberContact(
            @AuthMember final Long memberId,
            @RequestBody @Valid final MemberContactInfoPatchRequest request
    ) {
        memberService.patchMemberContact(memberId, request);
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
