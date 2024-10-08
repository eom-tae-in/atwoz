package com.atwoz.mission.application.membermission.event;


import com.atwoz.profile.domain.vo.Gender;

public record MemberMissionClearedEvent(
        Long memberId,
        Gender memberGender,
        Long missionId
) {
}
