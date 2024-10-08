//package com.atwoz.profile.application;
//
//import com.atwoz.hobby.exception.exceptions.HobbyNotFoundException;
//import com.atwoz.member.domain.member.Member;
//import com.atwoz.member.domain.member.MemberRepository;
//import com.atwoz.member.domain.member.vo.MemberGrade;
//import com.atwoz.member.infrastructure.member.MemberFakeRepository;
//import com.atwoz.memberlike.domain.MemberLikeRepository;
//import com.atwoz.memberlike.infrastructure.MemberLikeFakeRepository;
//import com.atwoz.profile.application.dto.NicknameDuplicationCheckResponse;
//import com.atwoz.profile.application.dto.ProfileFilterRequest;
//import com.atwoz.profile.domain.Profile;
//import com.atwoz.profile.domain.ProfileRepository;
//import com.atwoz.profile.domain.vo.Gender;
//import com.atwoz.profile.exception.exceptions.UserNicknameAlreadyExistedException;
//import com.atwoz.profile.infrastructure.ProfileFakeRepository;
//import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성;
//import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성_회원등급;
//import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_주입;
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_필터_요청_픽스처.프로필_필터_요청_생성;
//import static com.atwoz.profile.fixture.프로필_요청_픽스처.프로필_필터_요청_픽스처.프로필_필터_요청_생성_취미코드;
//import static com.atwoz.profile.fixture.프로필_응답_픽스처.추천_프로필_조회_응답_픽스처.추천_프로필_조회_응답_생성_프로필;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_성별;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_성별_위치;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_성별_종교;
//import static com.atwoz.profile.fixture.프로필_픽스처.프로필_생성_회원아이디_성별_취미목록;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//
//@DisplayNameGeneration(ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class ProfileQueryServiceTest {
//
//    private ProfileQueryService profileQueryService;
//
//    private ProfileRepository profileRepository;
//
//    private MemberRepository memberRepository;
//
//    private MemberLikeRepository memberLikeRepository;
//
//    private Profile profile;
//
//    @BeforeEach
//    void setup() {
//        memberRepository = new MemberFakeRepository();
//        profileRepository = new ProfileFakeRepository();
//        profileQueryService = new ProfileQueryService(profileRepository);
//        memberLikeRepository = new MemberLikeFakeRepository();
//        profile = 프로필_생성();
//        profileRepository.save(profile);
//    }
//
//
//    @Nested
//    class 닉네임_중복_확인 {
//
//        @Test
//        void 중복된_닉네임이_존재하지_않으면_예외가_false를_담은_객체_반환하고_예외가_발생하지_않는다() {
//            // given
//            String uniqueNickname = "uniqueNickname";
//
//            // when
//            NicknameDuplicationCheckResponse response = profileQueryService.checkNickNameDuplicated(uniqueNickname);
//
//            // then
//            assertThat(response.isDuplicated()).isFalse();
//        }
//
//        @Test
//        void 현재_사용하려는_닉네임이_이미_존재하면_예외가_발생한다() {
//            // given
//            String nickname = profile.getNickname();
//
//            // when & then
//            assertThatThrownBy(() -> profileQueryService.checkNickNameDuplicated(nickname))
//                    .isInstanceOf(UserNicknameAlreadyExistedException.class);
//        }
//    }
//
//    @Test
//    void 오늘의_이성_추천_프로필을_조회한다() {
//        // given
//        ProfileFilterRequest request = 프로필_필터_요청_생성();
//        Long memberId = profile.getId();
//        Member diamondMember = memberRepository.save(회원_생성_회원등급(MemberGrade.DIAMOND));
//        Member goldMember = memberRepository.save(회원_생성_회원등급(MemberGrade.GOLD));
//        Member anotherGoldMember = memberRepository.save(회원_생성_회원등급(MemberGrade.GOLD));
//        Profile goldProfile = profileRepository.save(프로필_생성_회원아이디(diamondMember.getId()));
//        Profile diamondProfile = profileRepository.save(프로필_생성_회원아이디(goldMember.getId()));
//        Profile anotherGoldProfile = profileRepository.save(프로필_생성_회원아이디(anotherGoldMember.getId()));
//
//        // when
//        List<ProfileRecommendationResponse> profileResponses = profileQueryService.findTodayProfiles(
//                memberId,
//                request
//        );
//
//        // then
//        assertSoftly(softly -> {
//            softly.assertThat(profileResponses).hasSize(3);
//
//            ProfileRecommendationResponse goldProfileResponse = profileResponses.get(0);
//            ProfileRecommendationResponse expectedGoldProfileResponse = 추천_프로필_조회_응답_생성_프로필(goldProfile);
//            softly.assertThat(goldProfileResponse)
//                    .usingRecursiveComparison()
//                    .isEqualTo(expectedGoldProfileResponse);
//
//            ProfileRecommendationResponse anotherGoldProfileResponse = profileResponses.get(1);
//            ProfileRecommendationResponse expectedAnotherGoldProfileResponse = 추천_프로필_조회_응답_생성_프로필(anotherGoldProfile);
//            softly.assertThat(anotherGoldProfileResponse)
//                    .usingRecursiveComparison()
//                    .isEqualTo(expectedAnotherGoldProfileResponse);
//
//            ProfileRecommendationResponse diamondProfileResponse = profileResponses.get(2);
//            ProfileRecommendationResponse expectedDiamondProfileResponse = 추천_프로필_조회_응답_생성_프로필(diamondProfile);
//            softly.assertThat(diamondProfileResponse)
//                    .usingRecursiveComparison()
//                    .isEqualTo(expectedDiamondProfileResponse);
//        });
//    }
//
//    @Test
//    void 인기있는_이성_프로필을_조회한다() {
//        // given
//        ProfileFilterRequest request = 프로필_필터_요청_생성();
//        Long memberId = profile.getId();
//        Member newMember = memberRepository.save(회원_생성());
//        Gender oppositeGender = findOppositeGender();
//        Profile newMemberProfile = profileRepository.save(프로필_생성_회원아이디_성별(newMember.getId(), oppositeGender));
//        memberLikeRepository.save(호감_생성_id_주입(memberId, newMember.getId()));
//
//        // when
//        ProfileRecommendationResponse profileResponse = profileQueryService.findProfileByPopularity(
//                memberId,
//                request
//        );
//
//        // then
//        ProfileRecommendationResponse expectedProfileResponse = 추천_프로필_조회_응답_생성_프로필(newMemberProfile);
//        assertThat(profileResponse).usingRecursiveComparison()
//                .isEqualTo(expectedProfileResponse);
//    }
//
//    private Gender findOppositeGender() {
//        Gender gender = profile.getPhysicalProfile().getGender();
//
//        if (gender.equals(Gender.MALE)) {
//            return Gender.FEMALE;
//        }
//
//        return Gender.MALE;
//    }
//
//    @Test
//    void 오늘_방문한_이성_프로필을_조회한다() {
//        // given
//        ProfileFilterRequest request = 프로필_필터_요청_생성();
//        Member newMember = memberRepository.save(회원_생성());
//        Gender oppositeGender = findOppositeGender();
//        Profile newMemberProfile = profileRepository.save(프로필_생성_회원아이디_성별(newMember.getId(), oppositeGender));
//
//        // when
//        ProfileRecommendationResponse profileResponse = profileQueryService.findProfileByTodayVisit(
//                profile.getId(),
//                request
//        );
//
//        // then
//        ProfileRecommendationResponse expectedProfileResponse = 추천_프로필_조회_응답_생성_프로필(newMemberProfile);
//        assertThat(profileResponse).usingRecursiveComparison()
//                .isEqualTo(expectedProfileResponse);
//    }
//
//    @Test
//    void 근처에_있는_이성_프로필을_조회한다() {
//        // given
//        ProfileFilterRequest request = 프로필_필터_요청_생성();
//        Gender oppositeGender = findOppositeGender();
//        Member nearByMember = memberRepository.save(회원_생성());
//        Profile nearByMemberProfile = 프로필_생성_회원아이디_성별_위치(nearByMember.getId(), oppositeGender, profile.getLocation());
//
//        // when
//        ProfileRecommendationResponse profileResponse = profileQueryService.findRecentProfile(
//                profile.getId(),
//                request
//        );
//
//        // then
//        ProfileRecommendationResponse expectedProfileResponse = 추천_프로필_조회_응답_생성_프로필(nearByMemberProfile);
//        assertThat(profileResponse).usingRecursiveComparison()
//                .isEqualTo(expectedProfileResponse);
//    }
//
//    @Test
//    void 최근에_가입한_이성_프로필을_조회한다() {
//        // given
//        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청_생성();
//        Member newMember = memberRepository.save(회원_생성());
//        Gender oppositeGender = findOppositeGender();
//        Profile newMemberProfile = profileRepository.save(프로필_생성_회원아이디_성별(newMember.getId(), oppositeGender));
//
//        // when
//        ProfileRecommendationResponse profileResponse = profileQueryService.findRecentProfile(
//                profile.getMemberId(),
//                profileFilterRequest
//        );
//
//        // then
//        ProfileRecommendationResponse expectedProfileResponse = 추천_프로필_조회_응답_생성_프로필(newMemberProfile);
//        assertThat(profileResponse).usingRecursiveComparison()
//                .isEqualTo(expectedProfileResponse);
//    }
//
//    @Test
//    void 종교가_같은_이성_프로필을_조회한다() {
//        // given
//        ProfileFilterRequest request = 프로필_필터_요청_생성();
//        Member sameReligionMember = memberRepository.save(회원_생성());
//        Gender oppositeGender = findOppositeGender();
//        Profile sameReligionMemberProfile = profileRepository.save(프로필_생성_회원아이디_성별_종교(
//                sameReligionMember.getId(),
//                oppositeGender,
//                profile.getReligion()
//        ));
//
//        // when
//        ProfileRecommendationResponse profileResponse = profileQueryService.findProfileByReligion(
//                profile.getMemberId(),
//                request
//        );
//
//        // then
//        ProfileRecommendationResponse expectedProfileResponse = 추천_프로필_조회_응답_생성_프로필(sameReligionMemberProfile);
//        assertThat(profileResponse).usingRecursiveComparison()
//                .isEqualTo(expectedProfileResponse);
//    }
//
//    @Nested
//    class 취미가_같은_이성_프로필_조회 {
//
//        @Test
//        void 취미가_같은_이성_프로필을_조회한다() {
//            // given
//            ProfileFilterRequest request = 프로필_필터_요청_생성();
//            Member sameHobbyMember = memberRepository.save(회원_생성());
//            Gender oppositeGender = findOppositeGender();
//            Profile sameHobbyMemberProfile = 프로필_생성_회원아이디_성별_취미목록(
//                    sameHobbyMember.getId(),
//                    oppositeGender,
//                    profile.getUserHobbies()
//            );
//
//            // when
//            ProfileRecommendationResponse profileResponse = profileQueryService.findProfileByHobbies(
//                    profile.getMemberId(),
//                    request
//            );
//
//            // then
//            ProfileRecommendationResponse expectedProfileResponse = 추천_프로필_조회_응답_생성_프로필(sameHobbyMemberProfile);
//            assertThat(profileResponse).usingRecursiveComparison()
//                    .isEqualTo(expectedProfileResponse);
//        }
//
//        @Test
//        void 없는_취미_코드가_필터_조건으로_넘어온_경우_예외가_발생한다() {
//            // given
//            String invalidHobbyCode = "invalidHobbyCode";
//            ProfileFilterRequest request = 프로필_필터_요청_생성_취미코드(invalidHobbyCode);
//
//            // when & then
//            assertThatThrownBy(() -> profileQueryService.findProfileByHobbies(profile.getMemberId(), request))
//                    .isInstanceOf(HobbyNotFoundException.class);
//        }
//    }
//}
