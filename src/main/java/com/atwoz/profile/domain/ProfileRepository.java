package com.atwoz.profile.domain;

import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository {

    boolean existsByNickname(String nickname);

    Profile save(Profile profile);

    Optional<Profile> findById(Long profileId);

    Optional<Long> findMemberIdByNickname(String nickname);

    List<ProfileRecommendationResponse> findTodayProfiles(ProfileFilterRequest request, Long memberId);

    ProfileRecommendationResponse findProfileByPopularity(ProfileFilterRequest request, Long memberId);

    ProfileRecommendationResponse findProfileByTodayVisit(ProfileFilterRequest request, Long memberId);

    ProfileRecommendationResponse findNearbyProfile(ProfileFilterRequest request, Long memberId);

    ProfileRecommendationResponse findRecentProfile(ProfileFilterRequest request, Long memberId);

    ProfileRecommendationResponse findProfileByReligion(ProfileFilterRequest request, Long memberId);

    ProfileRecommendationResponse findProfileByHobbies(ProfileFilterRequest request, Long memberId);

    void deleteById(Long profileId);
}
