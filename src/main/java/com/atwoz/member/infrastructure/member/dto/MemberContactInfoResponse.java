package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.vo.ContactType;

public record MemberContactInfoResponse(
        String contactType,
        String contactValue
) {

    public MemberContactInfoResponse(final ContactType contactType, final String contactValue) {
        this(contactType.getType(), contactValue);
    }
}
