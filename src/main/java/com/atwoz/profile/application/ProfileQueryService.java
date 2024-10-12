package com.atwoz.profile.application;

import com.atwoz.global.event.Events;
import com.atwoz.profile.application.dto.NicknameDuplicationCheckResponse;
import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.domain.ProfileRepository;
import com.atwoz.profile.domain.event.ProfileFetchStartedEvent;
import com.atwoz.profile.exception.exceptions.UserNicknameAlreadyExistedException;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProfileQueryService {

    private final ProfileRepository profileRepository;

    public NicknameDuplicationCheckResponse checkNickNameDuplicated(final String nickname) {
        if (profileRepository.existsByNickname(nickname)) {
            throw new UserNicknameAlreadyExistedException();
        }

        return NicknameDuplicationCheckResponse.from(false);
    }

    public List<ProfileRecommendationResponse> findTodayProfiles(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findTodayProfiles(request, memberId);
    }

    public ProfileRecommendationResponse findProfileByPopularity(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findProfileByPopularity(request, memberId);
    }

    public ProfileRecommendationResponse findProfileByTodayVisit(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findProfileByTodayVisit(request, memberId);
    }

    public ProfileRecommendationResponse findNearbyProfile(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findNearbyProfile(request, memberId);
    }

    public ProfileRecommendationResponse findRecentProfile(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findRecentProfile(request, memberId);
    }

    public ProfileRecommendationResponse findProfileByReligion(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findProfileByReligion(request, memberId);
    }

    public ProfileRecommendationResponse findProfileByHobbies(final Long memberId, final ProfileFilterRequest request) {
        Events.raise(new ProfileFetchStartedEvent(request.hobbyCode()));

        return profileRepository.findProfileByHobbies(request, memberId);
    }
}
