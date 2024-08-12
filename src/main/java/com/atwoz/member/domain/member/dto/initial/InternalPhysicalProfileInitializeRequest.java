package com.atwoz.member.domain.member.dto.initial;

import com.atwoz.member.application.member.dto.initial.PhysicalProfileInitialRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import lombok.Builder;

@Builder
public record InternalPhysicalProfileInitializeRequest(
        int birthYear,
        int height,
        YearManager yearManager
) {

    public static InternalPhysicalProfileInitializeRequest of(final PhysicalProfileInitialRequest request,
                                                              final YearManager yearManager) {
        return InternalPhysicalProfileInitializeRequest.builder()
                .birthYear(request.birthYear())
                .height(request.height())
                .yearManager(yearManager)
                .build();
    }
}
