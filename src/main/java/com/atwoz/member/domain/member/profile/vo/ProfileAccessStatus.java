package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.InvalidProfileAccessStatusException;
import java.util.Arrays;

public enum ProfileAccessStatus {
    PRIVATE("비공개"),
    WAITING("대기"),
    PUBLIC("공개");

    private final String status;

    ProfileAccessStatus(final String status) {
        this.status = status;
    }

    public static ProfileAccessStatus findBy(final String status) {
        return Arrays.stream(values())
                .filter(accessStatus -> status.equals(accessStatus.status))
                .findFirst()
                .orElseThrow(InvalidProfileAccessStatusException::new);
    }
}
