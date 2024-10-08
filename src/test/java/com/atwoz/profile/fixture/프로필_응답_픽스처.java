package com.atwoz.profile.fixture;

import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.vo.Location;
import com.atwoz.profile.domain.vo.PhysicalProfile;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;

@SuppressWarnings("NonAsciiCharacters")
public class 프로필_응답_픽스처 {

    public static class 추천_프로필_조회_응답_픽스처 {

        private static final String DEFAULT_NICKNAME = "nickname";
        private static final int DEFAULT_AGE = 30;
        private static final String DEFAULT_CITY = "서울시";
        private static final String DEFAULT_SECTOR = "강남구";
        private static final String DEFAULT_JOB_CODE = "jobCode";

        public static ProfileRecommendationResponse 추천_프로필_조회_응답_생성() {
            return new ProfileRecommendationResponse(
                    DEFAULT_NICKNAME,
                    DEFAULT_AGE,
                    DEFAULT_CITY,
                    DEFAULT_SECTOR,
                    DEFAULT_JOB_CODE
            );
        }

        public static ProfileRecommendationResponse 추천_프로필_조회_응답_생성_프로필(final Profile profile) {
            PhysicalProfile physicalProfile = profile.getPhysicalProfile();
            Location location = profile.getLocation();

            return new ProfileRecommendationResponse(
                    profile.getNickname(),
                    physicalProfile.getAge(),
                    location.getCity(),
                    location.getSector(),
                    profile.getJobCode()
            );
        }
    }
}
