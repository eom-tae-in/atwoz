package com.atwoz.profile.infrastructure.dto;

public record ProfileRecommendationResponse(
        String nickname,
        int age,
        String city,
        String sector,
        String jobCode
) {
}
