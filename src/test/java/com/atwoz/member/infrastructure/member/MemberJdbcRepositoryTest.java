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
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.fixture.member.generator.UniqueMemberFieldsGenerator;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_닉네임_전화번호_회원등급_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberFixture.회원_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.dto.request.InternalProfileFilterRequestFixture.내부_프로필_필터_요청_생성;
import static com.atwoz.member.fixture.member.dto.response.ProfileResponseFixture.프로필_응답서_생성_회원;
import static com.atwoz.member.fixture.member.generator.HobbyGenerator.취미_생성;
import static com.atwoz.member.fixture.member.generator.StyleGenerator.스타일_생성;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberJdbcRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberJdbcRepository memberJdbcRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private StyleRepository styleRepository;

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
    void 필터링_조건에_맞는_골드_등급_회원_최대_2명과_다이아_등급_회원_최대_1명을_조회한다() {
        // given
        List<Member> goldMembers = new ArrayList<>();
        IntStream.range(0, 5)
                .forEach(index -> {
                    Member savedMember = memberRepository.save(이성_회원_생성_등급(MemberGrade.GOLD));
                    goldMembers.add(savedMember);
                });
        List<Member> diamondMembers = new ArrayList<>();
        IntStream.range(0, 4)
                .forEach(index -> {
                    Member savedMember = memberRepository.save(이성_회원_생성_등급(MemberGrade.DIAMOND));
                    diamondMembers.add(savedMember);
                });
        InternalProfileFilterRequest filterRequest = 내부_프로필_필터_요청_생성();
        Long memberId = member.getId();

        // when
        List<ProfileResponse> profileResponses = memberJdbcRepository.findTodayProfiles(filterRequest, memberId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(profileResponses).hasSize(3);

            ProfileResponse goldMemberProfileResponse = profileResponses.get(0);
            ProfileResponse expectedGolMemberProfileResponse = 프로필_응답서_생성_회원(goldMembers.get(0));
            softly.assertThat(goldMemberProfileResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedGolMemberProfileResponse);

            ProfileResponse anotherGoldMemberProfileResponse = profileResponses.get(1);
            ProfileResponse expectedAnotherGoldMemberProfileResponse = 프로필_응답서_생성_회원(goldMembers.get(1));
            softly.assertThat(anotherGoldMemberProfileResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedAnotherGoldMemberProfileResponse);

            ProfileResponse diamondMemberProfileResponse = profileResponses.get(2);
            ProfileResponse expectedDiamondMemberProfileResponse = 프로필_응답서_생성_회원(diamondMembers.get(0));
            softly.assertThat(diamondMemberProfileResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedDiamondMemberProfileResponse);
        });
    }

    private Member 이성_회원_생성_등급(final MemberGrade memberGrade) {
        return 회원_생성_닉네임_전화번호_회원등급_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                uniqueMemberFieldsGenerator.generateNickname(),
                uniqueMemberFieldsGenerator.generatePhoneNumber(),
                memberGrade,
                ProfileAccessStatus.PUBLIC,
                ProfileAccessStatus.PUBLIC,
                Gender.FEMALE,
                hobbies,
                styles
        );
    }
}
