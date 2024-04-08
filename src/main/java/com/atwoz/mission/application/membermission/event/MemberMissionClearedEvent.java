package com.atwoz.mission.application.membermission.event;


import com.atwoz.member.domain.member.profile.physical.Gender;

public record MemberMissionClearedEvent(
        Long memberId,
        Gender memberGender,
        Long missionId
) {
}
