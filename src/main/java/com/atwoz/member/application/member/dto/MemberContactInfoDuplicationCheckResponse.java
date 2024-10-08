package com.atwoz.member.application.member.dto;

public record MemberContactInfoDuplicationCheckResponse(
        boolean isDuplicated
) {

    public static MemberContactInfoDuplicationCheckResponse from(final boolean isDuplicated) {
        return new MemberContactInfoDuplicationCheckResponse(isDuplicated);
    }
}
