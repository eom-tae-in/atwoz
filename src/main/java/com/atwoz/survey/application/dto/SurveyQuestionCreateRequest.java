package com.atwoz.survey.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SurveyQuestionCreateRequest(
        @NotBlank(message = "질문 내용이 작성되어야 합니다. (ex: 다음 중 좋아하는 여행 스타일은?)")
        String description
) {
}
