package com.atwoz.mission.application.membermission;

import com.atwoz.mission.application.membermission.event.MemberMissionClearedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberMissionsEventHandler {

    private final MemberMissionsService memberMissionsService;

    @EventListener
    public void addClearedMemberMission(final MemberMissionClearedEvent event) {
        memberMissionsService.addClearedMemberMission(event.memberId(), event.memberGender(), event.missionId());
    }
}
