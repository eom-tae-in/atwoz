package com.atwoz.member.infrastructure.member;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.atwoz.member.fixture.HobbiesResponseFixture.취미_응답서_요청;
import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static com.atwoz.member.fixture.MemberProfileResponseFixture.회원_프로필_응답서_요청;
import static com.atwoz.member.fixture.StylesResponseFixture.스타일_응답서_요청;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Test
    void 회원_조회() {
        // given
        Member member = 일반_유저_생성();
        memberRepository.save(member);

        // when
        MemberResponse memberResponse = memberQueryRepository.findMemberWithProfile(member.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberResponse.memberProfileResponse())
                    .usingRecursiveComparison()
                    .isEqualTo(회원_프로필_응답서_요청(member));
            softly.assertThat(memberResponse.hobbiesResponse())
                    .usingRecursiveComparison()
                    .isEqualTo(취미_응답서_요청(member));
            softly.assertThat(memberResponse.stylesResponse())
                    .usingRecursiveComparison()
                    .isEqualTo(스타일_응답서_요청(member));
        });
    }
}
