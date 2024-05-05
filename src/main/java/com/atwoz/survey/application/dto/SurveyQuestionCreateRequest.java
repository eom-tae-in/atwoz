package com.atwoz.survey.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SurveyQuestionCreateRequest(
        @NotBlank(message = "질문 내용이 작성되어야 합니다. (ex: 다음 중 좋아하는 여행 스타일은?)")
        String description,

        @NotBlank(message = "질문 답변 항목이 작성되어야 합니다. (ex: 무계획 여행)")
        List<String> answers
) {
}
