package com.atwoz.profile.infrastructure;

import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.ProfileRepository;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileJpaRepository profileJpaRepository;
    private final ProfileQueryRepository profileQueryRepository;

    @Override
    public boolean existsByNickname(final String nickname) {
        return profileJpaRepository.existsByNickname(nickname);
    }

    @Override
    public Profile save(final Profile profile) {
        return profileJpaRepository.save(profile);
    }

    @Override
    public Optional<Profile> findById(final Long profileId) {
        return profileJpaRepository.findById(profileId);
    }

    @Override
    public Optional<Long> findMemberIdByNickname(final String nickname) {
        return profileJpaRepository.findMemberIdByNickname(nickname);
    }

    @Override
    public List<ProfileRecommendationResponse> findTodayProfiles(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        return profileQueryRepository.findTodayProfiles(request, memberId);
    }

    @Override
    public ProfileRecommendationResponse findProfileByPopularity(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        return profileQueryRepository.findProfileByPopularity(request, memberId);
    }

    @Override
    public ProfileRecommendationResponse findProfileByTodayVisit(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        return profileQueryRepository.findProfileByTodayVisit(request, memberId);
    }

    @Override
    public ProfileRecommendationResponse findNearbyProfile(final ProfileFilterRequest request, final Long memberId) {
        return profileQueryRepository.findNearbyProfile(request, memberId);
    }

    @Override
    public ProfileRecommendationResponse findRecentProfile(final ProfileFilterRequest request, final Long memberId) {
        return profileQueryRepository.findRecentProfile(request, memberId);
    }

    @Override
    public ProfileRecommendationResponse findProfileByReligion(final ProfileFilterRequest request,
                                                               final Long memberId) {
        return profileQueryRepository.findProfileByReligion(request, memberId);
    }

    @Override
    public ProfileRecommendationResponse findProfileByHobbies(final ProfileFilterRequest request, final Long memberId) {
        return profileQueryRepository.findProfileByHobbies(request, memberId);
    }

    @Override
    public void deleteById(final Long profileId) {
        profileJpaRepository.deleteById(profileId);
    }
}
