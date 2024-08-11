package com.atwoz.member.fixture.member.dto.response;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;

@SuppressWarnings("NonAsciiCharacters")
public class ProfileResponseFixture {

    private static final String DEFAULT_NICKNAME = "nickname";
    private static final int DEFAULT_AGE = 30;
    private static final String DEFAULT_CITY = "서울시";
    private static final String DEFAULT_SECTOR = "강남구";
    private static final String DEFAULT_JOB_CODE = Job.RESEARCH_AND_DEVELOP.getCode();

    public static ProfileResponse 프로필_응답서_생성() {
        return new ProfileResponse(
                DEFAULT_NICKNAME,
                DEFAULT_AGE,
                DEFAULT_CITY,
                DEFAULT_SECTOR,
                DEFAULT_JOB_CODE
        );
    }

    public static ProfileResponse 프로필_응답서_생성_회원(final Member member) {
        Location location = member.getMemberProfile()
                .getProfile()
                .getLocation();
        PhysicalProfile physicalProfile = member.getMemberProfile()
                .getProfile()
                .getPhysicalProfile();
        Job job = member.getMemberProfile()
                .getProfile()
                .getJob();

        return new ProfileResponse(
                member.getNickname(),
                physicalProfile.getAge(),
                location.getCity(),
                location.getSector(),
                job.getCode()
        );
    }
}
