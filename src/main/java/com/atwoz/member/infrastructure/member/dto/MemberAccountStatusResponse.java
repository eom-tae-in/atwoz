package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.vo.MemberAccountStatus;

public record MemberAccountStatusResponse(
        String status
) {

    public MemberAccountStatusResponse(final MemberAccountStatus memberAccountStatus) {
        this(memberAccountStatus.getStatus());
    }
}
