package com.atwoz.member.application.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfileInitializeRequest(
        @NotNull(message = "출생년도를 작성해주세요.")
        Integer birthYear,

        @NotNull(message = "키를 입력해주세요.")
        Integer height,

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
        String mbti,

        @Valid
        @NotNull(message = "취미 정보를 입력해주세요.")
        HobbiesRequest hobbiesRequest,

        @Valid
        @NotNull(message = "스타일 정보를 입력해주세요.")
        StylesRequest stylesRequest
) {
}
