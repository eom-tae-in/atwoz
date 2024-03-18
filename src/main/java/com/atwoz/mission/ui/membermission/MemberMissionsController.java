package com.atwoz.mission.ui.membermission;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.mission.application.membermission.MemberMissionsQueryService;
import com.atwoz.mission.application.membermission.MemberMissionsService;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionPagingResponse;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import com.atwoz.mission.ui.membermission.dto.RewardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/missions")
@RestController
public class MemberMissionsController {

    private final MemberMissionsService memberMissionsService;
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

    @PostMapping("/reward")
    public ResponseEntity<RewardResponse> receiveAllRewards(@AuthMember final Long memberId) {
        Integer reward = memberMissionsService.getAllClearMissionsRewards(memberId);
        return ResponseEntity.ok()
                .body(new RewardResponse(reward));
    }

    @PostMapping("/{missionId}")
    public ResponseEntity<Void> addMemberMission(@AuthMember final Long memberId, @PathVariable final Long missionId) {
        memberMissionsService.addMemberMission(memberId, missionId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/{missionId}/reward")
    public ResponseEntity<RewardResponse> receiveRewardByMissionId(@AuthMember final Long memberId,
                                                                   @PathVariable final Long missionId) {
        Integer reward = memberMissionsService.getRewardByMissionId(memberId, missionId);
        return ResponseEntity.ok()
                .body(new RewardResponse(reward));
    }

    @PatchMapping("/{missionId}/clear")
    public ResponseEntity<Void> clearMission(@AuthMember final Long memberId, @PathVariable final Long missionId) {
        memberMissionsService.clearMemberMission(memberId, missionId);
        return ResponseEntity.ok()
                .build();
    }
}
