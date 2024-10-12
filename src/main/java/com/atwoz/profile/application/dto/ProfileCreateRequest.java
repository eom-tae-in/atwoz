package com.atwoz.profile.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProfileCreateRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,

        String recommender,

        @NotBlank(message = "직업 코드를 입력해주세요.")
        String jobCode,

        @NotBlank(message = "최종학력 정보를 입력해주세요.")
        String graduate,

        @NotBlank(message = "흡연을 입력해주세요.")
        String smoke,

        @NotBlank(message = "음주 정보를 입력해주세요.")
        String drink,

        @NotBlank(message = "종교 정보를 입력해주세요.")
        String religion,

        @NotBlank(message = "mbti를 입력해주세요.")
        String mbti,

        @NotNull(message = "출생년도를 작성해주세요.")
        Integer birthYear,

        @NotNull(message = "키를 입력해주세요.")
        Integer height,

        @NotBlank(message = "성별을 입력해주세요.")
        String gender,

        @NotBlank(message = "거주하고 있는 광역시/도를 입력해주세요.")
        String city,

        @NotBlank(message = "거주하고 있는 시/군/자치구 입력해주세요.")
        String sector,

        @NotEmpty(message = "취미 코드를 입력해주세요.")
        List<String> hobbyCodes
) {
}

