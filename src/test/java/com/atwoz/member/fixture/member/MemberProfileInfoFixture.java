package com.atwoz.member.fixture.member;

import com.atwoz.member.application.member.dto.ProfileInitializeRequest;
import com.atwoz.member.application.member.dto.ProfileUpdateRequest;
import com.atwoz.member.domain.member.dto.MemberProfileDto;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;

@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileInfoFixture {

    public static MemberProfileDto 회원_프로필_정보_생성(final ProfileInitializeRequest profileInitializeRequest) {
        return MemberProfileDto.createWith(profileInitializeRequest, new FakeYearManager());
    }

    public static MemberProfileDto 회원_프로필_정보_생성(final ProfileUpdateRequest profileUpdateRequest) {
        return MemberProfileDto.createWith(profileUpdateRequest, new FakeYearManager());
    }
}
