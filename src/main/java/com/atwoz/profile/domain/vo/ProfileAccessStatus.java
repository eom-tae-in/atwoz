package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.InvalidProfileAccessStatusException;
import java.util.Arrays;
import lombok.Getter;

@Getter
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
                .filter(accessStatus -> status.equals(accessStatus.getStatus()))
                .findFirst()
                .orElseThrow(InvalidProfileAccessStatusException::new);
    }
}
