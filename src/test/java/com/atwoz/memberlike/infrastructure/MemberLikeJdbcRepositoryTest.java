package com.atwoz.memberlike.infrastructure;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.만료된_호감_생성;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.옛날_호감_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberLikeJdbcRepositoryTest extends IntegrationHelper {

    private static final int MINUS_DAY_FOR_DELETE_NOT_ANSWERED_LIKE = 31;
    private static final int MINUS_DAY_FOR_UPDATE_RECENT_LIKE = 3;

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 호감을_보낸_지_30일이_지나도_응답이_없는_호감은_삭제된다() {
        // given
        LocalDateTime pastTime = LocalDateTime.now().minusDays(MINUS_DAY_FOR_DELETE_NOT_ANSWERED_LIKE);
        auditingHandler.setDateTimeProvider(() -> Optional.of(pastTime));
        MemberLike memberLike = 만료된_호감_생성();
        memberLikeRepository.save(memberLike);

        // when
        memberLikeRepository.deleteExpiredLikes();

        // then
        assertThat(memberLikeRepository.isAlreadyExisted(memberLike.getSenderId(), memberLike.getReceiverId()))
                .isEqualTo(false);
    }

    @Test
    @Transactional
    void 호감을_보낸_지_48시간이_지나면_최근_여부가_변경된다() {
        // given
        LocalDateTime pastTime = LocalDateTime.now().minusDays(MINUS_DAY_FOR_UPDATE_RECENT_LIKE);
        auditingHandler.setDateTimeProvider(() -> Optional.of(pastTime));

        MemberLike memberLike = 옛날_호감_생성();
        entityManager.persist(memberLike);
        entityManager.flush();
        entityManager.clear();

        // when
        memberLikeRepository.endRecentByTimeExpired();

        // then
        MemberLike findMemberLike = entityManager.find(MemberLike.class, memberLike.getId());
        assertThat(findMemberLike.getIsRecent()).isEqualTo(false);
    }
}
