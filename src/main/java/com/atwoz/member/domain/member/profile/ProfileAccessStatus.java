package com.atwoz.member.domain.member.profile;

public enum ProfileAccessStatus {
    PRIVATE("비공개"),
    WAITING("대기"),
    PUBLIC("공개");

    private final String status;

    ProfileAccessStatus(final String status) {
        this.status = status;
    }
}
