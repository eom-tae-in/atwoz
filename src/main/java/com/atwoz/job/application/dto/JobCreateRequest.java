package com.atwoz.job.application.dto;

import jakarta.validation.constraints.NotBlank;

public record JobCreateRequest(
        @NotBlank(message = "직업 이름을 입력해주세요.")
        String name,

        @NotBlank(message = "직업 코드를 입력해주세요.")
        String code
){
}
