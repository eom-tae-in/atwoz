package com.atwoz.profile.application;

import com.atwoz.profile.application.dto.ProfileCreateRequest;
import com.atwoz.profile.application.dto.ProfileUpdateRequest;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.ProfileRepository;
import com.atwoz.profile.domain.YearManager;
import com.atwoz.profile.exception.exceptions.ProfileNotFoundException;
import com.atwoz.profile.exception.exceptions.UnauthorizedProfileModificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final YearManager yearManager;

    public void saveProfile(final Long memberId, final ProfileCreateRequest request) {
        Profile profile = createProfile(memberId, request);
        profileRepository.save(profile);
    }

    private Profile createProfile(final Long memberId, final ProfileCreateRequest request) {
        Long foundRecommenderId = findRecommenderIdWithRecommenderNickname(request.recommender());

        return Profile.createWith(
                memberId,
                foundRecommenderId,
                request.nickname(),
                request.jobCode(),
                request.graduate(),
                request.smoke(),
                request.drink(),
                request.religion(),
                request.mbti(),
                request.birthYear(),
                request.height(),
                request.gender(),
                request.city(),
                request.sector(),
                request.hobbyCodes(),
                yearManager
        );
    }

    private Long findRecommenderIdWithRecommenderNickname(final String nickname) {
        return profileRepository.findMemberIdByNickname(nickname)
                .orElse(null);
    }

    public void updateProfile(
            final Long memberId,
            final Long profileId,
            final ProfileUpdateRequest request
    ) {
        Profile foundProfile = findProfile(profileId);
        validateModifierIsAuthor(memberId, foundProfile.getMemberId());
        foundProfile.update(
                request.nickname(),
                request.jobCode(),
                request.graduate(),
                request.smoke(),
                request.drink(),
                request.religion(),
                request.mbti(),
                request.birthYear(),
                request.height(),
                request.gender(),
                request.city(),
                request.sector(),
                request.hobbyCodes(),
                yearManager
        );
    }

    private Profile findProfile(final Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(ProfileNotFoundException::new);
    }

    private void validateModifierIsAuthor(final Long modifierId, final Long authorId) {
        if (!modifierId.equals(authorId)) {
            throw new UnauthorizedProfileModificationException();
        }
    }

    public void deleteProfile(final Long memberId, final Long profileId) {
        Profile foundProfile = findProfile(profileId);
        validateModifierIsAuthor(memberId, foundProfile.getMemberId());

        profileRepository.deleteById(profileId);
    }
}
