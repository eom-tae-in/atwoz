package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.MemberHobby;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.fixture.member.dto.response.ProfileResponseFixture;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.MemberLikeFakeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.member.fixture.member.dto.response.MemberResponseFixture.회원_정보_응답서_요청;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFakeRepository implements MemberRepository {

    private final Map<Long, Member> map = new HashMap<>();
    private Long id = 1L;
    private final MemberLikeRepository memberLikeFakeRepository;

    public MemberFakeRepository(final MemberLikeRepository memberLikeFakeRepository) {
        this.memberLikeFakeRepository = memberLikeFakeRepository;
    }

    public MemberFakeRepository() {
        this.memberLikeFakeRepository = new MemberLikeFakeRepository();
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Member> findByPhoneNumber(final String phoneNumber) {
        return map.values().stream()
                .filter(member -> phoneNumber.equals(member.getPhoneNumber()))
                .findAny();
    }

    @Override
    public Optional<Member> findByNickname(final String nickname) {
        return map.values().stream()
                .filter(member -> nickname.equals(member.getNickname()))
                .findAny();
    }

    @Override
    public MemberResponse findMemberWithId(final Long id) {
        Member member = map.get(id);

        return 회원_정보_응답서_요청(member);
    }

    @Override
    public List<ProfileResponse> findTodayProfiles(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                   final Long memberId) {
        Member foundMember = map.get(memberId);
        List<ProfileResponse> goldProfileResponses = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> member.getMemberGrade().equals(MemberGrade.GOLD))
                .filter(member -> isDifferentGender(member, foundMember))
                .limit(2)
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .toList();

        Optional<ProfileResponse> diamondProfileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> member.getMemberGrade().equals(MemberGrade.DIAMOND))
                .findFirst()
                .map(ProfileResponseFixture::프로필_응답서_생성_회원);

        List<ProfileResponse> profileResponses = new ArrayList<>(goldProfileResponses);
        diamondProfileResponse.ifPresent(profileResponses::add);

        return profileResponses;
    }

    @Override
    public ProfileResponse findProfileByPopularity(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                   final Long memberId) {
        Pageable pageable = PageRequest.of(0, 10);
        Member foundMember = map.get(memberId);
        Optional<ProfileResponse> profileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> isDifferentGender(member, foundMember))
                .sorted(Comparator.comparing(member ->
                        memberLikeFakeRepository.findReceivedLikesWithPaging(member.getId(), pageable)
                                .getTotalElements()
                ))
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .findFirst();

        return profileResponse.orElse(null);
    }

    @Override
    public ProfileResponse findProfileByTodayVisit(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                   final Long memberId) {
        Member foundMember = map.get(memberId);
        Optional<ProfileResponse> profileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> isDifferentGender(member, foundMember))
                .filter(this::isVisitedToday)
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .findFirst();

        return profileResponse.orElse(null);
    }

    @Override
    public ProfileResponse findNearbyProfile(final InternalProfileFilterRequest internalProfileFilterRequest,
                                             final Long memberId) {
        Member foundMember = map.get(memberId);
        Optional<ProfileResponse> profileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> isDifferentGender(member, foundMember))
                .filter(member -> isSameCity(member, foundMember))
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .findFirst();

        return profileResponse.orElse(null);
    }

    @Override
    public ProfileResponse findRecentProfile(final InternalProfileFilterRequest internalProfileFilterRequest,
                                             final Long memberId) {
        Member foundMember = map.get(memberId);
        Optional<ProfileResponse> profileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> isDifferentGender(member, foundMember))
                .sorted(Comparator.comparing(Member::getCreatedAt))
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .findFirst();

        return profileResponse.orElse(null);
    }

    @Override
    public ProfileResponse findProfileByReligion(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                 final Long memberId) {
        Member foundMember = map.get(memberId);
        Optional<ProfileResponse> profileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> isDifferentGender(member, foundMember))
                .filter(member -> isSameReligion(member, foundMember))
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .findFirst();

        return profileResponse.orElse(null);
    }

    @Override
    public ProfileResponse findProfileByHobbies(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                final Long memberId) {
        Member foundMember = map.get(memberId);
        Optional<ProfileResponse> profileResponse = map.values().stream()
                .filter(member -> applyFilterConditions(internalProfileFilterRequest, member))
                .filter(member -> isDifferentGender(member, foundMember))
                .filter(member -> hasSameHobby(member, foundMember))
                .map(ProfileResponseFixture::프로필_응답서_생성_회원)
                .findFirst();

        return profileResponse.orElse(null);
    }

    @Override
    public Member save(final Member member) {
        Member savedMember = Member.builder()
                .id(id)
                .recommenderId(member.getRecommenderId())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .memberProfile(member.getMemberProfile())
                .memberGrade(member.getMemberGrade())
                .memberStatus(member.getMemberStatus())
                .latestVisitDate(LocalDate.now())
                .build();

        map.put(id++, savedMember);

        return savedMember;
    }

    @Override
    public boolean existsByPhoneNumber(final String phoneNumber) {
        return map.values().stream()
                .anyMatch(member -> phoneNumber.equals(member.getPhoneNumber()));
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return map.values().stream()
                .anyMatch(member -> nickname.equals(member.getNickname()));
    }

    @Override
    public boolean existsById(final Long id) {
        return map.keySet().stream()
                .anyMatch(id::equals);
    }

    @Override
    public void deleteById(final Long id) {
        map.remove(id);
        this.id--;
    }

    private boolean applyFilterConditions(final InternalProfileFilterRequest request, final Member member) {
        int memberAge = getPhysicalProfile(member).getAge();
        Profile profile = getProfile(member);
        List<String> hobbies = getHobbyCodes(member);
        String memberCity = getLocation(member).getCity();

        return (request.minAge() <= memberAge && memberAge <= request.maxAge()) &&
                (profile.getSmoke().equals(request.smoke())) &&
                (profile.getDrink().equals(request.drink())) &&
                (profile.getReligion().equals(request.religion())) &&
                (hobbies.contains(request.hobbyCode())) &&
                (request.cities().contains(memberCity));
    }

    private boolean isDifferentGender(final Member member1, final Member member2) {
        return !getPhysicalProfile(member1).getGender().equals(getPhysicalProfile(member2).getGender());
    }

    private boolean isVisitedToday(final Member member) {
        return member.getLatestVisitDate().equals(LocalDate.now());
    }

    private boolean isSameCity(final Member member1, final Member member2) {
        return getLocation(member1).getCity().equals(getLocation(member2).getCity());
    }

    private boolean isSameReligion(final Member member1, final Member member2) {
        return getProfile(member1).getReligion().equals(getProfile(member2).getReligion());
    }

    private boolean hasSameHobby(final Member searchedMember, final Member requester) {
        Set<String> requesterHobbyCodes = new HashSet<>(getHobbyCodes(requester));

        return getHobbyCodes(searchedMember).stream()
                .anyMatch(requesterHobbyCodes::contains);
    }

    private Profile getProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile();
    }

    private PhysicalProfile getPhysicalProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getPhysicalProfile();
    }

    private Location getLocation(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getLocation();
    }

    private List<String> getHobbyCodes(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getMemberHobbies()
                .getHobbies()
                .stream()
                .map(MemberHobby::getHobby)
                .map(Hobby::getCode)
                .toList();
    }
}
