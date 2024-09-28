package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberInterviewsJpaRepositoryTest {

    @Autowired
    private MemberInterviewsJpaRepository memberInterviewsJpaRepository;

    @Test
    void 회원_인터뷰_목록_생성() {
        // given
        Long memberId = 1L;
        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);

        // when
        MemberInterviews savedMemberInterviews = memberInterviewsJpaRepository.save(memberInterviews);

        // then
        assertThat(savedMemberInterviews).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(memberInterviews);
    }

    @Test
    void 회원_인터뷰_목록_조회() {
        // given
        Long memberId = 1L;
        MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
        MemberInterviews savedMemberInterviews = memberInterviewsJpaRepository.save(memberInterviews);

        // when
        Optional<MemberInterviews> result = memberInterviewsJpaRepository.findById(savedMemberInterviews.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isPresent();
            softly.assertThat(result.get()).usingRecursiveComparison()
                    .isEqualTo(savedMemberInterviews);
        });
    }
}
