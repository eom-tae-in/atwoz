package com.atwoz.member.infrastructure.member;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.HobbyRepository;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.dto.request.InternalProfileFilterRequestFixture.내부_프로필_필터_요청_생성;
import static com.atwoz.member.fixture.member.dto.response.MemberResponseFixture.회원_정보_응답서_요청;
import static com.atwoz.member.fixture.member.dto.response.ProfileResponseFixture.프로필_응답서_생성_회원;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_주입;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    private Member member;

    private List<Hobby> hobbies;

    private List<Style> styles;

    private UniqueMemberFieldsGenerator uniqueMemberFieldsGenerator;

    @BeforeEach
    void setup() {
        hobbies = List.of(취미_생성(hobbyRepository, "hobby1", "code1"));
        styles = List.of(스타일_생성(styleRepository, "style1", "code1"));
        member = 회원_생성_취미목록_스타일목록(hobbies, styles);
        memberRepository.save(member);
        uniqueMemberFieldsGenerator = new UniqueMemberFieldsGenerator();
    }

    @Test
    void 회원_조회() {
        // when
        MemberResponse memberResponse = memberQueryRepository.findMemberWithProfile(member.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(회원_정보_응답서_요청(member));
        });
    }

    @Nested
    class 이성_회원_프로필_조회 {

        @Test
        void 가장_인기있는_이성_프로필을_조회한다() {
            // given
            InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
            Long memberId = member.getId();
            Member mostPopularMember = 가장_인기있는_이성_회원_생성();

            // when
            ProfileResponse profileResponse = memberQueryRepository.findProfileByPopularity(filterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(mostPopularMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }

        @Test
        void 오늘_방문한_이성_프로필을_조회한다() {
            // given
            InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
            Long memberId = member.getId();
            Member newMember = 새로운_이성_회원_생성();

            // when
            ProfileResponse profileResponse = memberQueryRepository.findProfileByTodayVisit(filterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }

        @Test
        void 근처에_있는_이성_프로필을_조회한다() {
            // given
            InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
            Long memberId = member.getId();
            Member newMember = 새로운_이성_회원_생성();

            // when
            ProfileResponse profileResponse = memberQueryRepository.findNearbyProfile(filterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }

        @Test
        void 최근에_가입한_이성_프로필을_조회한다() {
            // given
            InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
            Long memberId = member.getId();
            Member newMember = 새로운_이성_회원_생성();

            // when
            ProfileResponse profileResponse = memberQueryRepository.findRecentProfile(filterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }

        @Test
        void 종교가_같은_이성_프로필을_조회한다() {
            // given
            InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
            Long memberId = member.getId();
            Member newMember = 새로운_이성_회원_생성();

            // when
            ProfileResponse profileResponse = memberQueryRepository.findProfileByReligion(filterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }

        @Test
        void 취미가_같은_이성_프로필을_조회한다() {
            // given
            InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
            Long memberId = member.getId();
            Member newMember = 새로운_이성_회원_생성();

            // when
            ProfileResponse profileResponse = memberQueryRepository.findProfileByHobbies(filterRequest, memberId);

            // then
            ProfileResponse expectedProfileResponse = 프로필_응답서_생성_회원(newMember);
            assertThat(profileResponse).usingRecursiveComparison()
                    .isEqualTo(expectedProfileResponse);
        }
    }

    private Member 가장_인기있는_이성_회원_생성() {
        Member savedMember = memberRepository.save(
                회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                        uniqueMemberFieldsGenerator.generateNickname(),
                        uniqueMemberFieldsGenerator.generatePhoneNumber(),
                        ProfileAccessStatus.PUBLIC,
                        ProfileAccessStatus.PUBLIC,
                        Gender.FEMALE,
                        hobbies,
                        styles
                )
        );
        memberLikeRepository.save(호감_생성_id_주입(member.getId(), savedMember.getId()));

        return savedMember;
    }

    private Member 새로운_이성_회원_생성() {
        return memberRepository.save(
                회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                        uniqueMemberFieldsGenerator.generateNickname(),
                        uniqueMemberFieldsGenerator.generatePhoneNumber(),
                        ProfileAccessStatus.PUBLIC,
                        ProfileAccessStatus.PUBLIC,
                        Gender.FEMALE,
                        hobbies,
                        styles
                )
        );
    }
}
