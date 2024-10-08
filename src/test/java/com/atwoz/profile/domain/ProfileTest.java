//package com.atwoz.profile.domain;
//
//import com.atwoz.profile.application.dto.ProfileUpdateRequest;
//import com.atwoz.profile.domain.vo.Drink;
//import com.atwoz.profile.domain.vo.Graduate;
//import com.atwoz.profile.domain.vo.Mbti;
//import com.atwoz.profile.domain.vo.Religion;
//import com.atwoz.profile.domain.vo.Smoke;
//import com.atwoz.profile.infrastructure.FakeYearManager;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
//import org.junit.jupiter.api.Test;
//
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_수정_요청_픽스처.프로필_수정_요청_생성;
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_수정_요청_픽스처.프로필_수정_요청_생성_프로필;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayNameGeneration(ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class ProfileTest {
//
//    private YearManager yearManager;
//
//    @BeforeEach
//    void setup() {
//        yearManager = new FakeYearManager();
//    }
//
//    @Test
//    void 프로필을_생성한다() {
//        // given
//        Long memberId = 1L;
//        Long recommenderId = 1L;
//        String nickname = "nickname";
//        String jobCode = "jobCode";
//        String graduate = Graduate.SEOUL_FOURTH.getName();
//        String smoke = Smoke.NEVER.getName();
//        String drink = Drink.NEVER.getName();
//        String religion = Religion.NONE.getName();
//        String mbti = Mbti.ENFJ.name();
//        int birthYear = 2000;
//        int height = 180;
//        String gender = "남성";
//        String city = "서울시";
//        String sector = "강남구";
//        List<String> userHobbies = List.of("hobbyCode1", "hobbyCode2");
//
//        // when
//        Profile profile = Profile.createWith(
//                memberId,
//                recommenderId,
//                nickname,
//                jobCode,
//                graduate,
//                smoke,
//                drink,
//                religion,
//                mbti,
//                birthYear,
//                height,
//                gender,
//                city,
//                sector,
//                userHobbies,
//                yearManager
//        );
//
//        // then
//        Profile expectedProfile = 프로필_생성();
//        assertThat(profile).usingRecursiveComparison()
//                .isEqualTo(expectedProfile);
//    }
//
//    @Test
//    void 프로필을_변경한다() {
//        // given
//        Profile profile = 프로필_생성();
//        ProfileUpdateRequest request = 프로필_수정_요청_생성();
//
//        // when
//        profile.update(
//                request.nickname(),
//                request.jobCode(),
//                request.graduate(),
//                request.smoke(),
//                request.drink(),
//                request.religion(),
//                request.mbti(),
//                request.birthYear(),
//                request.height(),
//                request.gender(),
//                request.city(),
//                request.sector(),
//                request.hobbyCodes(),
//                yearManager
//        );
//
//        // then
//        ProfileUpdateRequest expected = 프로필_수정_요청_생성_프로필(profile);
//        assertThat(request).usingRecursiveComparison()
//                .isEqualTo(expected);
//    }
//}
