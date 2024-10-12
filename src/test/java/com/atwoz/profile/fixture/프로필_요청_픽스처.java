package com.atwoz.profile.fixture;

import com.atwoz.profile.application.dto.ProfileCreateRequest;
import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.application.dto.ProfileUpdateRequest;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.UserHobby;
import com.atwoz.profile.domain.vo.Drink;
import com.atwoz.profile.domain.vo.Graduate;
import com.atwoz.profile.domain.vo.Mbti;
import com.atwoz.profile.domain.vo.Religion;
import com.atwoz.profile.domain.vo.Smoke;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 프로필_요청_픽스처 {

    public static class 프로필_생성_요청_픽스처 {

        private static final String DEFAULT_NICKNAME = "nickname";
        private static final String DEFAULT_RECOMMENDER = "recommender";
        private static final String DEFAULT_JOB_CODE = "jobCode";
        private static final String DEFAULT_GRADUATE = Graduate.SEOUL_FOURTH.getName();
        private static final String DEFAULT_SMOKE = Smoke.NEVER.getName();
        private static final String DEFAULT_DRINK = Drink.NEVER.getName();
        private static final String DEFAULT_RELIGION = Religion.NONE.getName();
        private static final String DEFAULT_MBTI = Mbti.ENFJ.name();
        private static final int DEFAULT_BIRTH_YEAR = 2000;
        private static final int DEFAULT_HEIGHT = 180;
        private static final String DEFAULT_GENDER = "남성";
        private static final String DEFAULT_CITY = "서울시";
        private static final String DEFAULT_SECTOR = "강남구";
        private static final List<String> DEFAULT_USER_HOBBIES = List.of("hobbyCode1", "hobbyCode2");

        public static ProfileCreateRequest 프로필_생성_요청_생성() {
            return new ProfileCreateRequest(
                    DEFAULT_NICKNAME,
                    DEFAULT_RECOMMENDER,
                    DEFAULT_JOB_CODE,
                    DEFAULT_GRADUATE,
                    DEFAULT_SMOKE,
                    DEFAULT_DRINK,
                    DEFAULT_RELIGION,
                    DEFAULT_MBTI,
                    DEFAULT_BIRTH_YEAR,
                    DEFAULT_HEIGHT,
                    DEFAULT_GENDER,
                    DEFAULT_CITY,
                    DEFAULT_SECTOR,
                    DEFAULT_USER_HOBBIES
            );
        }

        public static ProfileCreateRequest 프로필_생성_요청_생성_프로필(final Profile profile) {
            return new ProfileCreateRequest(
                    profile.getNickname(),
                    DEFAULT_RECOMMENDER,
                    profile.getJobCode(),
                    profile.getGraduate().getName(),
                    profile.getSmoke().getName(),
                    profile.getDrink().getName(),
                    profile.getReligion().getName(),
                    profile.getMbti().name(),
                    DEFAULT_BIRTH_YEAR,
                    profile.getPhysicalProfile().getHeight(),
                    profile.getPhysicalProfile().getGender().getName(),
                    profile.getLocation().getCity(),
                    profile.getLocation().getSector(),
                    profile.getUserHobbies().getHobbies().stream().map(UserHobby::getHobbyCode).toList()
            );
        }
    }

    public static class 프로필_필터_요청_픽스처 {

        private static final Integer DEFAULT_MIN_AGE = 25;
        private static final Integer DEFAULT_MAX_AGE = 35;
        private static final String DEFAULT_SMOKE = "비흡연";
        private static final String DEFAULT_DRINK = "전혀 마시지 않음";
        private static final String DEFAULT_RELIGION = "무교";
        private static final String DEFAULT_HOBBY_CODE = "code1";
        private static final List<String> DEFAULT_CITIES = List.of("서울시", "경기도");

        public static ProfileFilterRequest 프로필_필터_요청_생성() {
            return new ProfileFilterRequest(
                    DEFAULT_MAX_AGE,
                    DEFAULT_MIN_AGE,
                    DEFAULT_SMOKE,
                    DEFAULT_DRINK,
                    DEFAULT_RELIGION,
                    DEFAULT_HOBBY_CODE,
                    DEFAULT_CITIES
            );
        }

        public static ProfileFilterRequest 프로필_필터_요청_생성_취미코드(final String hobbyCode) {
            return new ProfileFilterRequest(
                    DEFAULT_MIN_AGE,
                    DEFAULT_MAX_AGE,
                    DEFAULT_SMOKE,
                    DEFAULT_DRINK,
                    DEFAULT_RELIGION,
                    hobbyCode,
                    DEFAULT_CITIES
            );
        }
    }

    public static class 프로필_수정_요청_픽스처 {

        private static final String DEFAULT_NICKNAME = "nickname";
        private static final String DEFAULT_JOB_CODE = "jobCode";
        private static final String DEFAULT_GRADUATE = Graduate.SEOUL_FOURTH.getName();
        private static final String DEFAULT_SMOKE = Smoke.NEVER.getName();
        private static final String DEFAULT_DRINK = Drink.NEVER.getName();
        private static final String DEFAULT_RELIGION = Religion.NONE.getName();
        private static final String DEFAULT_MBTI = Mbti.ENFJ.name();
        private static final int DEFAULT_BIRTH_YEAR = 2000;
        private static final int DEFAULT_HEIGHT = 180;
        private static final String DEFAULT_GENDER = "남성";
        private static final String DEFAULT_CITY = "서울시";
        private static final String DEFAULT_SECTOR = "강남구";
        private static final List<String> DEFAULT_USER_HOBBIES = List.of("hobbyCode1", "hobbyCode2");

        public static ProfileUpdateRequest 프로필_수정_요청_생성() {
            return new ProfileUpdateRequest(
                    DEFAULT_NICKNAME,
                    DEFAULT_JOB_CODE,
                    DEFAULT_GRADUATE,
                    DEFAULT_SMOKE,
                    DEFAULT_DRINK,
                    DEFAULT_RELIGION,
                    DEFAULT_MBTI,
                    DEFAULT_BIRTH_YEAR,
                    DEFAULT_HEIGHT,
                    DEFAULT_GENDER,
                    DEFAULT_CITY,
                    DEFAULT_SECTOR,
                    DEFAULT_USER_HOBBIES
            );
        }

        public static ProfileUpdateRequest 프로필_수정_요청_생성_프로필(final Profile profile) {
            return new ProfileUpdateRequest(
                    profile.getNickname(),
                    profile.getJobCode(),
                    profile.getGraduate().getName(),
                    profile.getSmoke().getName(),
                    profile.getDrink().getName(),
                    profile.getReligion().getName(),
                    profile.getMbti().name(),
                    DEFAULT_BIRTH_YEAR,
                    profile.getPhysicalProfile().getHeight(),
                    profile.getPhysicalProfile().getGender().getName(),
                    profile.getLocation().getCity(),
                    profile.getLocation().getSector(),
                    profile.getUserHobbies().getHobbies().stream().map(UserHobby::getHobbyCode).toList()
            );
        }
    }
}
