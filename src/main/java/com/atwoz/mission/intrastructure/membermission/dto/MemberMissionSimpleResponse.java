package com.atwoz.mission.intrastructure.membermission.dto;

import com.atwoz.mission.domain.mission.vo.MissionType;
import java.time.LocalDateTime;

public record MemberMissionSimpleResponse(
        Long missionId,
        Boolean doesGetReward,
        Integer reward,
        MissionType missionType,
        LocalDateTime createdAt
) {
}
