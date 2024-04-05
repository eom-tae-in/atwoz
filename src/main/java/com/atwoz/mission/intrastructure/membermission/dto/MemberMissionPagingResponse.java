package com.atwoz.mission.intrastructure.membermission.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record MemberMissionPagingResponse(
        List<MemberMissionSimpleResponse> memberMissions,
        int nextPage
) {

    public static MemberMissionPagingResponse of(final Page<MemberMissionSimpleResponse> memberMissions, final int nextPage) {
        return new MemberMissionPagingResponse(memberMissions.getContent(), nextPage);
    }
}
