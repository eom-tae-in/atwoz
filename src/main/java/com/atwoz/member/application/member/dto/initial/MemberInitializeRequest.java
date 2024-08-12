package com.atwoz.member.application.member.dto.initial;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MemberInitializeRequest(
        @Valid
        @NotNull(message = "프로필 정보를 입력해주세요")
        ProfileInitializeRequest profileInitializeRequest,

        @NotBlank(message = "닉네입을 입력해주세요.")
        String nickname,

        String recommenderNickname
) {

    @JsonIgnore
    public PhysicalProfileInitialRequest getPhysicalProfileInitialRequest() {
        return profileInitializeRequest.physicalProfileInitialRequest();
    }

    @JsonIgnore
    public List<String> getHobbyCodes() {
        return profileInitializeRequest.getHobbyCodes();
    }

    @JsonIgnore
    public List<String> getStyleCodes() {
        return profileInitializeRequest.getStyleCodes();
    }
}

