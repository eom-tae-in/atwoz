package com.atwoz.member.application.selfintro.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SelfIntroFilterRequest(

        @NotNull(message = "최소 나이를 입력해주세요")
        Integer minAge,

        @NotNull(message = "최대 나이를 입력해주세요")
        Integer maxAge,

        @NotNull(message = "성별을 선택해주세요")
        Boolean isOnlyOppositeGender,

        @Valid
        @NotEmpty(message = "선호 지역을 하나 이상 입력해주세요.")
        List<CityRequest> cityRequests
) {

    public List<String> getCities() {
        return cityRequests.stream()
                .map(CityRequest::city)
                .toList();
    }
}
