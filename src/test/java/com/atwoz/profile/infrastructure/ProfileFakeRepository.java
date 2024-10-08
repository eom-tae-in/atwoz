package com.atwoz.profile.infrastructure;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.infrastructure.MemberLikeFakeRepository;
import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.ProfileRepository;
import com.atwoz.profile.domain.UserHobby;
import com.atwoz.profile.domain.vo.Gender;
import com.atwoz.profile.domain.vo.Location;
import com.atwoz.profile.domain.vo.ProfileAccessStatus;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import static com.atwoz.profile.fixture.프로필_응답_픽스처.추천_프로필_조회_응답_픽스처;

@SuppressWarnings("NonAsciiCharacters")
public class ProfileFakeRepository implements ProfileRepository {

    private static final int REQUIRED_DIAMOND_MEMBER_FETCH_COUNT = 2;
    private static final int REQUIRED_GOLD_MEMBER_FETCH_COUNT = 3;
    private static final int DEFAULT_FETCH_COUNT = 10;

    private final Map<Long, Profile> map = new HashMap<>();
    private Long id = 1L;

    private final MemberFakeRepository memberFakeRepository;
    private final MemberLikeFakeRepository memberLikeFakeRepository;

    public ProfileFakeRepository() {
        memberFakeRepository = new MemberFakeRepository();
        memberLikeFakeRepository = new MemberLikeFakeRepository();
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return map.values()
                .stream()
                .anyMatch(profile -> nickname.equals(profile.getNickname()));
    }

    @Override
    public Profile save(final Profile profile) {
        Profile savedProfile = Profile.builder()
                .id(id)
                .memberId(profile.getMemberId())
                .recommenderId(profile.getRecommenderId())
                .nickname(profile.getNickname())
                .jobCode(profile.getJobCode())
                .graduate(profile.getGraduate())
                .smoke(profile.getSmoke())
                .drink(profile.getDrink())
                .religion(profile.getReligion())
                .mbti(profile.getMbti())
                .profileAccessStatusByAdmin(profile.getProfileAccessStatusByAdmin())
                .profileAccessStatusByUser(profile.getProfileAccessStatusByUser())
                .physicalProfile(profile.getPhysicalProfile())
                .location(profile.getLocation())
                .userHobbies(profile.getUserHobbies())
                .build();

        map.put(id++, savedProfile);

        return savedProfile;
    }

    @Override
    public Optional<Profile> findById(final Long profileId) {
        return Optional.ofNullable(map.get(profileId));
    }

    @Override
    public Optional<Long> findMemberIdByNickname(final String nickname) {
        return map.values()
                .stream()
                .filter(profile -> nickname.equals(profile.getNickname()))
                .findAny()
                .map(Profile::getMemberId);
    }

    @Override
    public List<ProfileRecommendationResponse> findTodayProfiles(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Profile foundProfile = findProfileByMemberId(memberId);

        if (Objects.isNull(foundProfile)) {
            return Collections.emptyList();
        }

        List<ProfileRecommendationResponse> foundDiamondGradeProfiles = findProfiles(
                request,
                foundProfile,
                MemberGrade.DIAMOND,
                REQUIRED_DIAMOND_MEMBER_FETCH_COUNT
        );

        int remainingFetchCount = REQUIRED_GOLD_MEMBER_FETCH_COUNT +
                REQUIRED_DIAMOND_MEMBER_FETCH_COUNT -
                foundDiamondGradeProfiles.size();

        List<ProfileRecommendationResponse> foundGoldGradeProfiles = findProfiles(
                request,
                foundProfile,
                MemberGrade.GOLD,
                remainingFetchCount
        );

        return mergeFoundProfiles(foundDiamondGradeProfiles, foundGoldGradeProfiles);
    }

    private List<ProfileRecommendationResponse> findProfiles(
            final ProfileFilterRequest request,
            final Profile foundProfile,
            final MemberGrade memberGrade,
            final int count
    ) {
        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .filter(profile -> isMemberInGrade(profile.getMemberId(), memberGrade))
                .limit(count)
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .toList();
    }

    private boolean isMemberInGrade(final Long memberId, final MemberGrade memberGrade) {
        Member foundMember = memberFakeRepository.findById(memberId)
                .orElse(null);

        if (Objects.isNull(foundMember)) {
            return false;
        }

        return memberGrade.equals(foundMember.getMemberGrade());
    }

    private List<ProfileRecommendationResponse> mergeFoundProfiles(
            final List<ProfileRecommendationResponse> diamondGradeProfiles,
            final List<ProfileRecommendationResponse> goldGradeProfiles
    ) {
        List<ProfileRecommendationResponse> profileResponses = new ArrayList<>();
        profileResponses.addAll(diamondGradeProfiles);
        profileResponses.addAll(goldGradeProfiles);

        return profileResponses;
    }

    @Override
    public ProfileRecommendationResponse findProfileByPopularity(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Profile foundProfile = findProfileByMemberId(memberId);
        List<Long> foundPopularMemberIds = findPopularMemberIds();

        if (Objects.isNull(foundProfile) || Objects.isNull(foundPopularMemberIds) || foundPopularMemberIds.isEmpty()) {
            return null;
        }

        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .filter(profile -> foundPopularMemberIds.contains(profile.getMemberId()))
                .findFirst()
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .orElse(null);
    }

    // 10개만 조회
    private List<Long> findPopularMemberIds() {
        Map<Long, Integer> receiverLikeCountMap = new HashMap<>();

        for (MemberLike memberLike : memberLikeFakeRepository.getMemberLikes()) {
            Long receiverId = memberLike.getReceiverId();
            receiverLikeCountMap.put(receiverId, receiverLikeCountMap.getOrDefault(receiverId, 0) + 1);
        }

        return receiverLikeCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .limit(DEFAULT_FETCH_COUNT)
                .map(Entry::getKey)
                .toList();
    }

    @Override
    public ProfileRecommendationResponse findProfileByTodayVisit(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Profile foundProfile = findProfileByMemberId(memberId);
        List<Long> foundMemberIds = findTodayVisitedMemberIds();

        if (Objects.isNull(foundProfile) || Objects.isNull(foundMemberIds) || foundMemberIds.isEmpty()) {
            return null;
        }

        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .filter(profile -> foundMemberIds.contains(profile.getMemberId()))
                .findAny()
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .orElse(null);
    }

    // 10개만 조회
    private List<Long> findTodayVisitedMemberIds() {
        List<Member> members = memberFakeRepository.getMembers();
        return members.stream()
                .filter(member -> member.getLatestVisitDate().equals(LocalDate.now()))
                .limit(DEFAULT_FETCH_COUNT)
                .map(Member::getId)
                .toList();
    }

    @Override
    public ProfileRecommendationResponse findNearbyProfile(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Profile foundProfile = findProfileByMemberId(memberId);

        if (Objects.isNull(foundProfile)) {
            return null;
        }

        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .filter(profile -> isSameCity(profile, foundProfile))
                .findAny()
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .orElse(null);
    }

    private boolean isSameCity(final Profile filterdProfile, final Profile foundProfile) {
        Location filteredProfileLocation = filterdProfile.getLocation();
        Location foundProfileLocation = foundProfile.getLocation();

        return Objects.equals(filteredProfileLocation.getCity(), foundProfileLocation.getCity());
    }

    @Override
    public ProfileRecommendationResponse findRecentProfile(final ProfileFilterRequest request, final Long memberId) {
        Profile foundProfile = findProfileByMemberId(memberId);

        if (Objects.isNull(foundProfile)) {
            return null;
        }

        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .sorted(Comparator.comparing(Profile::getCreatedAt))
                .findAny()
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .orElse(null);
    }

    @Override
    public ProfileRecommendationResponse findProfileByReligion(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Profile foundProfile = findProfileByMemberId(memberId);

        if (Objects.isNull(foundProfile)) {
            return null;
        }

        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .filter(profile -> isSameReligion(profile, foundProfile))
                .findAny()
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .orElse(null);
    }

    private boolean isSameReligion(final Profile filteredProfile, final Profile foundProfile) {
        return Objects.equals(filteredProfile.getReligion(), foundProfile.getReligion());
    }

    @Override
    public ProfileRecommendationResponse findProfileByHobbies(final ProfileFilterRequest request, final Long memberId) {
        Profile foundProfile = findProfileByMemberId(memberId);

        if (Objects.isNull(foundProfile)) {
            return null;
        }

        return map.values()
                .stream()
                .filter(profile -> applyBaseRecommendProfileCondition(profile, foundProfile))
                .filter(profile -> applyFilterConditions(request, profile))
                .filter(profile -> hasSameHobby(profile, foundProfile))
                .findAny()
                .map(추천_프로필_조회_응답_픽스처::추천_프로필_조회_응답_생성_프로필)
                .orElse(null);
    }

    private boolean hasSameHobby(final Profile filteredProfile, final Profile foundProfile) {
        List<UserHobby> foundProfileUserHobbies = foundProfile.getUserHobbies()
                .getHobbies();

        return filteredProfile.getUserHobbies()
                .getHobbies()
                .stream()
                .anyMatch(foundProfileUserHobbies::contains);
    }

    @Override
    public void deleteById(final Long profileId) {
        map.remove(profileId);
    }

    private Profile findProfileByMemberId(final Long memberId) {
        return map.values()
                .stream()
                .filter(profile -> memberId.equals(profile.getMemberId()))
                .findAny()
                .orElse(null);
    }

    private boolean applyBaseRecommendProfileCondition(final Profile filterdProfile, final Profile foundProfile) {
        Gender filterdProfileGender = filterdProfile.getPhysicalProfile().getGender();
        Gender foundProfileGender = foundProfile.getPhysicalProfile().getGender();

        return filterdProfileGender != foundProfileGender &&
                filterdProfile.getProfileAccessStatusByAdmin() == ProfileAccessStatus.PUBLIC &&
                filterdProfile.getProfileAccessStatusByUser() == ProfileAccessStatus.PUBLIC;
    }

    private boolean applyFilterConditions(final ProfileFilterRequest request, final Profile profile) {
        int age = profile.getPhysicalProfile()
                .getAge();

        List<String> hobbyCodes = profile.getUserHobbies()
                .getHobbies()
                .stream()
                .map(UserHobby::getHobbyCode)
                .toList();

        return (request.minAge() <= age && age <= request.maxAge()) &&
                (profile.getSmoke().equals(request.smoke())) &&
                (profile.getDrink().equals(request.drink())) &&
                (profile.getReligion().equals(request.religion())) &&
                (hobbyCodes.contains(request.hobbyCode())) &&
                (request.cities().contains(profile.getLocation().getCity()));
    }
}
