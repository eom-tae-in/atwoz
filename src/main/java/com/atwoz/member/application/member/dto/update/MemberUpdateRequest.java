package com.atwoz.member.application.member.dto.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MemberUpdateRequest(
        @Valid
        @NotNull(message = "프로필 정보를 입력해주세요.")
        ProfileUpdateRequest profileUpdateRequest,

        @NotBlank(message = "닉네입을 입력해주세요.")
        String nickname
) {

    @JsonIgnore
    public PhysicalProfileUpdateRequest getPhysicalProfileInitialRequest() {
        return profileUpdateRequest.physicalProfileUpdateRequest();
    }

    @JsonIgnore
    public List<String> getHobbyCodes() {
        return profileUpdateRequest.getHobbyCodes();
    }

    @JsonIgnore
    public List<String> getStyleCodes() {
        return profileUpdateRequest.getStyleCodes();
    }
}
