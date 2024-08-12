package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.ProfileFilterRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.MemberHobby;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import com.atwoz.member.infrastructure.member.profile.hobby.HobbyFakeRepository;
import com.atwoz.member.infrastructure.member.profile.style.StyleFakeRepository;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.MemberLikeFakeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_도시_구역_성별;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_성별;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_성별_취미목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_종교_성별;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원등급_성별;
import static com.atwoz.member.fixture.member.dto.request.ProfileFilterRequestFixture.프로필_필터_요청서_생성;
import static com.atwoz.member.fixture.member.dto.request.ProfileFilterRequestFixture.프로필_필터_요청서_생성_취미코드;
import static com.atwoz.member.fixture.member.dto.response.MemberResponseFixture.회원_정보_응답서_요청;
import static com.atwoz.member.fixture.member.dto.response.ProfileResponseFixture.프로필_응답서_생성_회원;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_목록_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_목록_생성;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_주입;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberQueryServiceTest {

    private MemberQueryService memberQueryService;

    private MemberRepository memberRepository;

    private MemberLikeRepository memberLikeRepository;

    private Member member;

    private UniqueMemberFieldsGenerator uniqueMemberFieldsGenerator;

    @BeforeEach
    void setup() {
        HobbyRepository hobbyRepository = new HobbyFakeRepository();
        memberRepository = new MemberFakeRepository();
        memberQueryService = new MemberQueryService(memberRepository, hobbyRepository);
        memberLikeRepository = new MemberLikeFakeRepository();
        member = memberRepository.save(회원_생성());
        uniqueMemberFieldsGenerator = new UniqueMemberFieldsGenerator();
        취미_목록_생성(hobbyRepository);
        스타일_목록_생성(new StyleFakeRepository());
    }

    @Nested
    class 닉네임_중복_확인 {

        @Test
        void 중복된_닉네임이_존재하지_않으면_예외가_발생하지_않는다() {
            // given
            String uniqueNickname = "uniqueNickname";

            // when & then
            assertDoesNotThrow(() -> memberQueryService.checkMemberExists(uniqueNickname));
        }

        @Test
        void 중복된_닉네임이_존재하면_예외가_발생한다() {
            // given
            String nickname = member.getNickname();

            // when & then
            assertThatThrownBy(() -> memberQueryService.checkMemberExists(nickname))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
        }
    }

    @Test
    void 회원을_조회한다() {
        // given
        Long memberId = member.getId();

        // when
        MemberResponse memberResponse = memberQueryService.findMember(memberId);

        // then
        MemberResponse expectedMemberResponse = 회원_정보_응답서_요청(member);
        assertSoftly(softly -> {
            softly.assertThat(memberResponse).usingRecursiveComparison()
                    .isEqualTo(expectedMemberResponse);
        });
    }

    @Test
    void 오늘의_이성_추천_프로필을_조회한다() {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        Long memberId = member.getId();
        Member diamondMember = memberRepository.save(다이아_등급_이성_회원_생성());
        Member goldMember = memberRepository.save(골드_등급_이성_회원_생성());
        Member anotherGoldMember = memberRepository.save(골드_등급_이성_회원_생성());

        // when
        List<ProfileResponse> profileResponses = memberQueryService.findTodayProfiles(profileFilterRequest, memberId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(profileResponses).hasSize(3);

            ProfileResponse goldProfileResponse = profileResponses.get(0);
            ProfileResponse expectedGoldProfileResponse = 프로필_응답서_생성_회원(goldMember);
            softly.assertThat(goldProfileResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedGoldProfileResponse);

            ProfileResponse anotherGoldProfileResponse = profileResponses.get(1);
            ProfileResponse expectedAnotherGoldProfileResponse = 프로필_응답서_생성_회원(anotherGoldMember);
            softly.assertThat(anotherGoldProfileResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedAnotherGoldProfileResponse);

            ProfileResponse diamondProfileResponse = profileResponses.get(2);
            ProfileResponse expectedDiamondProfileResponse = 프로필_응답서_생성_회원(diamondMember);
            softly.assertThat(diamondProfileResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedDiamondProfileResponse);
        });
    }

    @Test
    void 인기있는_이성_프로필을_조회한다() {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        Long memberId = member.getId();
        Member newMember = memberRepository.save(새로운_이성_회원_생성());
        memberLikeRepository.save(호감_생성_id_주입(newMember.getId(), memberId));

        // when
        ProfileResponse profileResponse = memberQueryService.findProfileByPopularity(profileFilterRequest, memberId);

        // then
        ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(expectedProfileResponse);
    }

    @Test
    void 오늘_방문한_이성_프로필을_조회한다() {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        Member newMember = memberRepository.save(새로운_이성_회원_생성());
        Long memberId = member.getId();

        // when
        ProfileResponse profileResponse = memberQueryService.findProfileByTodayVisit(profileFilterRequest, memberId);

        // then
        ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(expectedProfileResponse);
    }

    @Test
    void 근처에_있는_이성_프로필을_조회한다() {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        Member nearByMember = memberRepository.save(근처에_있는_이성_회원_생성(member));
        Long memberId = member.getId();

        // when
        ProfileResponse profileResponse = memberQueryService.findRecentProfile(profileFilterRequest, memberId);

        // then
        ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(nearByMember);
        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(expectedProfileResponse);
    }

    @Test
    void 최근에_가입한_이성_프로필을_조회한다() {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        Member newMember = memberRepository.save(새로운_이성_회원_생성());
        Long memberId = member.getId();

        // when
        ProfileResponse profileResponse = memberQueryService.findRecentProfile(profileFilterRequest, memberId);

        // then
        ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(expectedProfileResponse);
    }

    @Test
    void 종교가_같은_이성_프로필을_조회한다() {
        // given
        ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
        Member sameReligionMember = memberRepository.save(종교가_같은_이성_회원_생성(member));
        Long memberId = member.getId();

        // when
        ProfileResponse profileResponse = memberQueryService.findProfileByReligion(profileFilterRequest, memberId);

        // then
        ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(sameReligionMember);
        assertThat(profileResponse).usingRecursiveComparison()
                .isEqualTo(expectedProfileResponse);
    }

    @Nested
    class 취미가_같은_이성_프로필_조회 {

        @Test
        void 취미가_같은_이성_프로필을_조회한다() {
            // given
            ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성();
            Member sameHobbyMember = memberRepository.save(취미가_같은_이성_회원_생성(member));
            Long memberId = member.getId();

            // when
            ProfileResponse profileResponse = memberQueryService.findProfileByHobbies(profileFilterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(sameHobbyMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }

        @Test
        void 없는_취미_코드가_필터_조건으로_넘어온_경우_예외가_발생한다() {
            // given
            String invalidHobbyCode = "invalidHobbyCode";
            ProfileFilterRequest profileFilterRequest = 프로필_필터_요청서_생성_취미코드(invalidHobbyCode);
            memberRepository.save(취미가_같은_이성_회원_생성(member));
            Long memberId = member.getId();

            // when & then
            assertThatThrownBy(() -> memberQueryService.findProfileByHobbies(profileFilterRequest, memberId))
                    .isInstanceOf(HobbyNotFoundException.class);
        }
    }

    private Member 다이아_등급_이성_회원_생성() {
        return 회원_생성_닉네임_전화번호_회원등급_성별(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                MemberGrade.DIAMOND,
                Gender.FEMALE
        );
    }

    private Member 골드_등급_이성_회원_생성() {
        return 회원_생성_닉네임_전화번호_회원등급_성별(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                MemberGrade.GOLD,
                Gender.FEMALE
        );
    }

    private Member 새로운_이성_회원_생성() {
        return 회원_생성_닉네임_전화번호_성별(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                Gender.FEMALE
        );
    }

    private Member 근처에_있는_이성_회원_생성(final Member member) {
        Location location = member.getMemberProfile()
                .getProfile()
                .getLocation();

        return 회원_생성_닉네임_전화번호_도시_구역_성별(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                location.getCity(),
                location.getSector(),
                Gender.FEMALE
        );
    }

    private Member 종교가_같은_이성_회원_생성(final Member member) {
        Religion religion = member.getMemberProfile()
                .getProfile()
                .getReligion();

        return 회원_생성_닉네임_전화번호_종교_성별(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                religion,
                Gender.FEMALE
        );
    }

    private Member 취미가_같은_이성_회원_생성(final Member member) {
        List<Hobby> hobbies = member.getMemberProfile()
                .getProfile()
                .getMemberHobbies()
                .getHobbies()
                .stream()
                .map(MemberHobby::getHobby)
                .toList();

        return 회원_생성_닉네임_전화번호_성별_취미목록(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                Gender.FEMALE,
                hobbies
        );
    }
}
