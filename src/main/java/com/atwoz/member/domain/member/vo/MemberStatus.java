package com.atwoz.member.domain.member.vo;

public enum MemberStatus {
    ACTIVE("활동중"),
    HUMAN("휴먼"),
    TEMPORARY_SUSPENSION("임시정지"),
    PERMANENT_BAN("영구정지");

    private final String status;

    MemberStatus(final String status) {
        this.status = status;
    }
}
