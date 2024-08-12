package com.atwoz.member.application.member.dto;

import jakarta.validation.Valid;
import java.util.List;

public record ProfileFilterRequest(
        @Valid
        List<ProfileCityFilterRequest> profileCityFilterRequests,
        Integer maxAge,
        Integer minAge,
        String smoke,
        String drink,
        String religion,
        String hobbyCode
) {
}
