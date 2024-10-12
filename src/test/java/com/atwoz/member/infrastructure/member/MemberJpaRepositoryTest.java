package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.vo.Contact;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberRepository;

    private Member member;


    // TODO: 이거 나중에 변경해야함
    @BeforeEach
    void setup() {
        member = Member.createWithOAuth("01011111111");
        memberRepository.save(member);
    }

    @Test
    void 회원을_저장한다() {
        // given
        Member createdMember = Member.createWithOAuth("01012345678");

        // when
        Member savedMember = memberRepository.save(createdMember);

        // then
        assertThat(savedMember).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(createdMember);
    }

    @Test
    void 아이디_값으로_회원을_찾는다() {
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
        void 연락처_정보로_회원의_존재를_확인한다() {
            // given
            Contact contact = member.getContact();

            // when
            boolean result = memberRepository.existsByContact(contact);

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
