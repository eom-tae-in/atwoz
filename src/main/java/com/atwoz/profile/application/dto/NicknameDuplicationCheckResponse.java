package com.atwoz.profile.application.dto;

public record NicknameDuplicationCheckResponse(
        boolean isDuplicated
){
    public static NicknameDuplicationCheckResponse from(final boolean isDuplicated) {
        return new NicknameDuplicationCheckResponse(isDuplicated);
    }
}
