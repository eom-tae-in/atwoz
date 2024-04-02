package com.atwoz.member.application.member.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record StylesRequest(
        @NotEmpty(message = "스타일 정보를 입력해주세요.")
        List<String> styles
) {
}
