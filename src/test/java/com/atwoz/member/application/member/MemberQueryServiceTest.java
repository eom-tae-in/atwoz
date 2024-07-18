package com.atwoz.member.application.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.HobbiesResponseFixture.취미_응답서_요청;
import static com.atwoz.member.fixture.member.MemberFixture.일반_유저_생성;
import static com.atwoz.member.fixture.member.MemberProfileResponseFixture.회원_프로필_응답서_요청;
import static com.atwoz.member.fixture.member.StylesResponseFixture.스타일_응답서_요청;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberQueryServiceTest {

    private MemberQueryService memberQueryService;
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository = new MemberFakeRepository();
        memberQueryService = new MemberQueryService(memberRepository);
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
            memberRepository.save(일반_유저_생성());
            String nickname = "nickname";

            // when & then
            assertThatThrownBy(() -> memberQueryService.checkMemberExists(nickname))
                    .isInstanceOf(MemberNicknameAlreadyExistedException.class);
        }
    }

    @Test
    void 회원을_조회한다() {
        // given
        Member member = memberRepository.save(일반_유저_생성());

        // when
        MemberResponse memberResponse = memberQueryService.findMember(member.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberResponse.memberProfileResponse()).usingRecursiveComparison()
                    .isEqualTo(회원_프로필_응답서_요청(member));
            softly.assertThat(memberResponse.hobbiesResponse()).usingRecursiveComparison()
                    .isEqualTo(취미_응답서_요청(member));
            softly.assertThat(memberResponse.stylesResponse()).usingRecursiveComparison()
                    .isEqualTo(스타일_응답서_요청(member));
        });
    }
}
