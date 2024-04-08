package com.atwoz.member.fixture;

import com.atwoz.member.application.member.dto.ProfileInitializeRequest;
import com.atwoz.member.application.member.dto.ProfileUpdateRequest;
import com.atwoz.member.domain.member.dto.MemberProfileInfo;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;

public class MemberProfileInfoFixture {

    public static MemberProfileInfo 회원_프로필_정보_생성(final ProfileInitializeRequest profileInitializeRequest) {

        return MemberProfileInfo.createWith(profileInitializeRequest, new FakeYearManager());
    }

    public static MemberProfileInfo 회원_프로필_정보_생성(final ProfileUpdateRequest profileUpdateRequest) {

        return MemberProfileInfo.createWith(profileUpdateRequest, new FakeYearManager());
    }
}
