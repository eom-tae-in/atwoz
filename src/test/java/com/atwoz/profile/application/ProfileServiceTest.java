package com.atwoz.profile.application;

import com.atwoz.profile.application.dto.ProfileCreateRequest;
import com.atwoz.profile.application.dto.ProfileUpdateRequest;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.ProfileRepository;
import com.atwoz.profile.domain.YearManager;
import com.atwoz.profile.exception.exceptions.ProfileNotFoundException;
import com.atwoz.profile.exception.exceptions.UnauthorizedProfileModificationException;
import com.atwoz.profile.infrastructure.FakeYearManager;
import com.atwoz.profile.infrastructure.ProfileFakeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_생성_요청_픽스처.프로필_생성_요청_생성;
import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_생성_요청_픽스처.프로필_생성_요청_생성_프로필;
import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_수정_요청_픽스처.프로필_수정_요청_생성;
import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProfileServiceTest {

    private ProfileRepository profileRepository;

    private ProfileService profileService;

    @BeforeEach
    void init() {
        profileRepository = new ProfileFakeRepository();
        YearManager yearManager = new FakeYearManager();
        profileService = new ProfileService(profileRepository, yearManager);
    }

    @Test
    void 프로필을_생성한다() {
        // given
        Long memberId = 1L;
        ProfileCreateRequest request = 프로필_생성_요청_생성();

        // when
        profileService.saveProfile(memberId, request);

        // then
        Optional<Profile> foundProfile = profileRepository.findById(1L);
        assertSoftly(softly -> {
                    softly.assertThat(foundProfile).isPresent();
                    ProfileCreateRequest expectedResponse = 프로필_생성_요청_생성_프로필(foundProfile.get());
                    softly.assertThat(expectedResponse).usingRecursiveComparison()
                            .ignoringFields("recommender", "birthYear")
                            .isEqualTo(expectedResponse);
                }
        );
    }

    @Nested
    class 프로필_수정 {
//
//        @Test
//        void 프로픨을_변경힌디() {
//            // given
//            Profile savedProfile = profileRepository.save(프로필_생성());
//            ProfileUpdateRequest request = 프로필_수정_요청_생성();
//
//            // when
//            profileService.updateProfile(savedProfile.getMemberId(), savedProfile.getId(), request);
//
//            // then
//            ProfileUpdateRequest expectedResponse = 프로필_수정_요청_생성_프로필(savedProfile);
//            assertThat(expectedResponse).usingRecursiveComparison()
//                    .ignoringFields("birthYear")
//                    .isEqualTo(request);
//        }

        @Test
        void 변경하려는_프로필이_존재하지_않으면_예외가_발생한다() {
            // given
            Profile savedProfile = profileRepository.save(프로필_생성());
            Long notExistProfileId = 999L;
            ProfileUpdateRequest request = 프로필_수정_요청_생성();

            // when
            assertThatThrownBy(() -> profileService.updateProfile(
                    savedProfile.getMemberId(),
                    notExistProfileId,
                    request
            )).isInstanceOf(ProfileNotFoundException.class);
        }

        @Test
        void 프로필_변경자와_작성자_정보가_다르면_예외가_발생한다() {
            // given
            Long stranger = 999L;
            Profile savedProfile = profileRepository.save(프로필_생성());
            ProfileUpdateRequest request = 프로필_수정_요청_생성();

            // when & then
            assertThatThrownBy(() -> profileService.updateProfile(stranger, savedProfile.getId(), request))
                    .isInstanceOf(UnauthorizedProfileModificationException.class);
        }
    }

    @Test
    void 프로필을_삭제한다() {
        // given
        Profile savedProfile = profileRepository.save(프로필_생성());

        // when
        profileService.deleteProfile(savedProfile.getMemberId(), savedProfile.getId());

        // then
        Optional<Profile> foundProfile = profileRepository.findById(savedProfile.getId());
        assertThat(foundProfile).isEmpty();
    }
}
