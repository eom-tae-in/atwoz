package com.atwoz.member.ui.member.dto;

import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import java.util.List;

public record TodayProfilesResponse(
        List<ProfileResponse> profileResponses
){
}
