package com.atwoz.profile.ui.dto;

import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import java.util.List;

public record TodayProfilesResponse(
        List<ProfileRecommendationResponse> profileRecommendationResponses
){
}
