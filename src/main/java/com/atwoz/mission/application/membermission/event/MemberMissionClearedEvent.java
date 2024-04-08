package com.atwoz.mission.application.membermission.event;

import com.atwoz.member.domain.info.profile.body.Gender;

public record MemberMissionClearedEvent(
        Long memberId,
        Gender memberGender,
        Long missionId
) {
}
