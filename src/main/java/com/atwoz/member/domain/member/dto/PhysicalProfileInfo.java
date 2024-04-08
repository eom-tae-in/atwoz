package com.atwoz.member.domain.member.dto;

import com.atwoz.member.domain.member.profile.physical.YearManager;

public record PhysicalProfileInfo(
        Integer birthYear,
        Integer height,
        YearManager yearManager
) {
}
