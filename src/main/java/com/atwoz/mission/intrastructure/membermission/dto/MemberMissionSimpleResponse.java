package com.atwoz.mission.intrastructure.membermission.dto;

public record MemberMissionSimpleResponse(
        Long missionId,
        Boolean doesGetReward,
        Integer reward
) {
}
