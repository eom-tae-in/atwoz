package com.atwoz.profile.ui;

import com.atwoz.member.ui.auth.support.AuthMember;
import com.atwoz.profile.application.ProfileQueryService;
import com.atwoz.profile.application.ProfileService;
import com.atwoz.profile.application.dto.NicknameDuplicationCheckResponse;
import com.atwoz.profile.application.dto.ProfileCreateRequest;
import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.application.dto.ProfileUpdateRequest;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import com.atwoz.profile.ui.dto.TodayProfilesResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileQueryService profileQueryService;

    @GetMapping("/{nickname}/duplication")
    public ResponseEntity<NicknameDuplicationCheckResponse> checkNickNameDuplicated(
            @PathVariable final String nickname
    ) {
        return ResponseEntity.ok(profileQueryService.checkNickNameDuplicated(nickname));
    }

    @PostMapping
    public ResponseEntity<Void> saveProfile(
            @AuthMember final Long memberId,
            @RequestBody @Valid final ProfileCreateRequest request
    ) {
        profileService.saveProfile(memberId, request);

        return ResponseEntity.status(CREATED)
                .build();
    }

    // TODO: 프로필 상세 조회 만들기

    @GetMapping("/today")
    public ResponseEntity<TodayProfilesResponse> findTodayProfiles(
            @AuthMember final Long memberId,
            @ModelAttribute final ProfileFilterRequest request
    ) {
        List<ProfileRecommendationResponse> todayProfiles = profileQueryService.findTodayProfiles(memberId, request);
        return ResponseEntity.ok(new TodayProfilesResponse(todayProfiles));
    }

    @GetMapping("/popularity")
    public ResponseEntity<ProfileRecommendationResponse> findProfileByPopularity(
            @AuthMember final Long memberId,
            @ModelAttribute final ProfileFilterRequest request
    ) {
        return ResponseEntity.ok(profileQueryService.findProfileByPopularity(memberId, request));
    }

    @GetMapping("/today-visit")
    public ResponseEntity<ProfileRecommendationResponse> findProfileByTodayVisit(
            @AuthMember final Long memberId,
            @ModelAttribute @Valid final ProfileFilterRequest request
    ) {
        return ResponseEntity.ok(profileQueryService.findProfileByTodayVisit(memberId, request));
    }

    @GetMapping("/nearby")
    public ResponseEntity<ProfileRecommendationResponse> findNearbyProfile(
            @AuthMember final Long memberId,
            @ModelAttribute @Valid final ProfileFilterRequest request
    ) {
        return ResponseEntity.ok(profileQueryService.findNearbyProfile(memberId, request));
    }

    @GetMapping("/recency")
    public ResponseEntity<ProfileRecommendationResponse> findRecentProfile(
            @AuthMember final Long memberId,
            @ModelAttribute @Valid final ProfileFilterRequest request
    ) {
        return ResponseEntity.ok(profileQueryService.findRecentProfile(memberId, request));
    }

    @GetMapping("/religion")
    public ResponseEntity<ProfileRecommendationResponse> findProfileByReligion(
            @AuthMember final Long memberId,
            @ModelAttribute @Valid final ProfileFilterRequest request
    ) {
        return ResponseEntity.ok(profileQueryService.findProfileByReligion(memberId, request));
    }

    @GetMapping("/hobbies")
    public ResponseEntity<ProfileRecommendationResponse> findProfileByHobbies(
            @AuthMember final Long memberId,
            @ModelAttribute @Valid final ProfileFilterRequest request
    ) {
        return ResponseEntity.ok(profileQueryService.findProfileByHobbies(memberId, request));
    }

    @PatchMapping("/{profileId}")
    public ResponseEntity<Void> updateProfile(
            @AuthMember final Long memberId,
            @PathVariable final Long profileId,
            @RequestBody @Valid final ProfileUpdateRequest request
    ) {
        profileService.updateProfile(memberId, profileId, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(
            @AuthMember final Long memberId,
            @PathVariable final Long profileId
    ) {
        profileService.deleteProfile(memberId, profileId);

        return ResponseEntity.noContent()
                .build();
    }
}
