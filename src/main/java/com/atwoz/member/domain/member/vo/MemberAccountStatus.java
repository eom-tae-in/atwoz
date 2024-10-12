package com.atwoz.member.domain.member.vo;

import com.atwoz.member.exception.exceptions.member.InvalidMemberAccountStatusException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MemberAccountStatus {

    ACTIVE("활동중"),
    INACTIVE("휴면"),
    TEMPORARY_SUSPENSION("임시정지"),
    PERMANENT_BAN("영구정지");

    private final String status;

    MemberAccountStatus(final String status) {
        this.status = status;
    }

    public static MemberAccountStatus findByStatus(final String status) {
        return Arrays.stream(values())
                .filter(memberAccountStatus -> status.equals(memberAccountStatus.getStatus()))
                .findFirst()
                .orElseThrow(InvalidMemberAccountStatusException::new);
    }

}
