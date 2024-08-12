package com.atwoz.member.application.member.dto.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProfileUpdateRequest(
        @Valid
        @NotNull(message = "신체 프로필 정보를 입력해주세요.")
        PhysicalProfileUpdateRequest physicalProfileUpdateRequest,

        @Valid
        @NotNull(message = "취미 정보를 입력해주세요.")
        HobbiesUpdateRequest hobbiesUpdateRequest,

        @Valid
        @NotNull(message = "스타일 정보를 입력해주세요.")
        StylesUpdateRequest stylesUpdateRequest,

        @NotBlank(message = "거주하고 있는 광역시/도를 입력해주세요.")
        String city,

        @NotBlank(message = "거주하고 있는 시/군/자치구 입력해주세요.")
        String sector,

        @NotBlank(message = "직업을 입력해주세요.")
        String job,

        @NotBlank(message = "최종학력 정보를 입력해주세요.")
        String graduate,

        @NotBlank(message = "음주 정보를 입력해주세요.")
        String drink,

        @NotBlank(message = "흡연을 입력해주세요.")
        String smoke,

        @NotBlank(message = "종교 정보를 입력해주세요.")
        String religion,

        @NotBlank(message = "mbti를 입력해주세요.")
        String mbti
) {

    @JsonIgnore
    public List<String> getHobbyCodes() {
        return hobbiesUpdateRequest.hobbyCodes();
    }

    @JsonIgnore
    public List<String> getStyleCodes() {
        return stylesUpdateRequest.styleCodes();
    }
}
