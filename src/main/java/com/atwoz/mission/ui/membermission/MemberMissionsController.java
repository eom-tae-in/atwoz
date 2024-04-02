package com.atwoz.mission.ui.membermission;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.mission.application.membermission.MemberMissionsQueryService;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionPagingResponse;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/missions")
@RestController
public class MemberMissionsController {

    private final MemberMissionsQueryService memberMissionsQueryService;

    @GetMapping
    public ResponseEntity<MemberMissionPagingResponse> findMemberMissionsWithPaging(
            @AuthMember final Long memberId,
            @PageableDefault(sort = "id", direction = DESC) final Pageable pageable
    ) {
        return ResponseEntity.ok(memberMissionsQueryService.findMemberMissionsWithPaging(memberId, pageable));
    }

    @GetMapping("/clear")
    public ResponseEntity<List<MemberMissionSimpleResponse>> findMissionsByStatus(
            @AuthMember final Long memberId,
            @RequestParam(value = "status", defaultValue = "false") final Boolean isStatusClear) {
        return ResponseEntity.ok(memberMissionsQueryService.findMemberMissionsByStatus(memberId, isStatusClear));
    }
}
