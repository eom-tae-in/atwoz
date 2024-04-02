package com.atwoz.member.application.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberNicknameRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname
){
}
