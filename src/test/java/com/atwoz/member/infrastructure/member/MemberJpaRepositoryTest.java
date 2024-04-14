package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberRepository;

    private Member member;

    @BeforeEach
    void setup() {
        member = 일반_유저_생성();
        memberRepository.save(member);
    }

    @Nested
    class 회원_조회 {

        @Test
        void 아이디_값으로_멤버를_찾는다() {
            // given
            Long memberId = member.getId();

            // when
            Optional<Member> result = memberRepository.findById(memberId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }

        @Test
        void 전화번호_값으로_멤버를_찾는다() {
            // given
            String phoneNumber = member.getPhoneNumber();

            // when
            Optional<Member> result = memberRepository.findByPhoneNumber(phoneNumber);

            // then
            assertSoftly(softly -> {
                softly.assertThat(result).isPresent();
                softly.assertThat(result.get()).usingRecursiveComparison().isEqualTo(member);
            });
        }
    }

    @Nested
    class 회원_존재_확인 {

        @Test
        void 아이디_값으로_회원의_존재를_확인한다() {
            // given
            Long memberId = member.getId();

            // when
            boolean result = memberRepository.existsById(memberId);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 전화번호_값으로_회원의_존재를_확인한다() {
            // given
            String phoneNumber = member.getPhoneNumber();

            // when
            boolean result = memberRepository.existsByPhoneNumber(phoneNumber);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 닉네임_값으로_회원의_존재를_확인한다() {
            // given
            String nickname = member.getNickname();

            // when
            boolean result = memberRepository.existsByNickname(nickname);

            // then
            assertThat(result).isTrue();
        }

    }

    @Test
    void 아이디_값으로_회원을_삭제한다() {
        // given
        Long memberId = member.getId();

        // when
        memberRepository.deleteById(memberId);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        // then
        assertThat(optionalMember).isNotPresent();
    }
}
