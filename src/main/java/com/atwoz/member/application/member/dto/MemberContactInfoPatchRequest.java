package com.atwoz.member.application.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberContactInfoPatchRequest(

        @NotBlank(message = "연락처 유형을 입력해주세요")
        String contactType,

        @NotBlank(message = "연락처 값을 입력해주세요")
        String contactValue
) {
}
