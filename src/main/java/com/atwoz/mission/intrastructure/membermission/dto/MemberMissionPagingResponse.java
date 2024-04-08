package com.atwoz.mission.intrastructure.membermission.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public record MemberMissionPagingResponse(
        List<MemberMissionSimpleResponse> memberMissions,
        int nextPage
) {

    public static MemberMissionPagingResponse of(final Page<MemberMissionSimpleResponse> memberMissions, final int nextPage) {
        return new MemberMissionPagingResponse(memberMissions.getContent(), nextPage);
    }
}
