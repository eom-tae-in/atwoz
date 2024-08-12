package com.atwoz.member.application.member.dto.initial;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record StylesInitializeRequest(
        @NotEmpty(message = "스타일 코드를 입력해주세요.")
        List<String> styleCodes
) {
}
