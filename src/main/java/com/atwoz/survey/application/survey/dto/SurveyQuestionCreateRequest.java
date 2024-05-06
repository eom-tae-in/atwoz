package com.atwoz.survey.application.survey.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SurveyQuestionCreateRequest(
        @NotBlank(message = "질문 내용이 작성되어야 합니다. (ex: 다음 중 좋아하는 여행 스타일은?)")
        String description,

        @NotNull(message = "질문 답변 항목이 작성되어야 합니다. (ex: 무계획 여행)")
        List<String> answers
) {
}
