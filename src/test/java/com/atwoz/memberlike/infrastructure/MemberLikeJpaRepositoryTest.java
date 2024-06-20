package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.domain.MemberLike;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_없음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class MemberLikeJpaRepositoryTest {

    @Autowired
    private MemberLikeJpaRepository memberLikeJpaRepository;

    @Test
    void 호감_생성() {
        // given
        MemberLike memberLike = 호감_생성_id_없음();

        // when
        MemberLike savedMemberLike = memberLikeJpaRepository.save(memberLike);

        // then
        assertThat(memberLike).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(savedMemberLike);
    }

    @Test
    void 호감을_생성하면_조회된다() {
        // given
        MemberLike memberLike = 호감_생성_id_없음();
        memberLikeJpaRepository.save(memberLike);

        // when & then
        assertThat(memberLikeJpaRepository.existsBySenderIdAndReceiverId(memberLike.getSenderId(), memberLike.getReceiverId()))
                .isEqualTo(true);
    }
}
