package com.atwoz.member.domain.member.dto.update;

import com.atwoz.member.application.member.dto.update.PhysicalProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import lombok.Builder;

@Builder
public record InternalPhysicalProfileUpdateRequest(
        int birthYear,
        int height,
        YearManager yearManager
) {

    public static InternalPhysicalProfileUpdateRequest of(final PhysicalProfileUpdateRequest request,
                                                          final YearManager yearManager) {
        return InternalPhysicalProfileUpdateRequest.builder()
                .birthYear(request.birthYear())
                .height(request.height())
                .yearManager(yearManager)
                .build();
    }
}
