package com.atwoz.memberlike.application;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.MemberLikeFakeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikePagingResponse;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberLikeQueryServiceTest {

    private MemberLikeQueryService memberLikeQueryService;
    private MemberLikeRepository memberLikeRepository;

    @BeforeEach
    void init() {
        memberLikeRepository = new MemberLikeFakeRepository();
        memberLikeQueryService = new MemberLikeQueryService(memberLikeRepository);
    }

    @Test
    void 호감을_보낸_회원을_조회한다() {
        // given
        Long senderId = 1L;
        List<Long> receivers = new ArrayList<>();
        for (long id = 2L; id <= 15L; id++) {
            memberLikeRepository.save(MemberLike.createWith(senderId, id, "관심있어요"));
            receivers.add(id);
        }
        PageRequest request = PageRequest.of(0, 9);

        // when
        MemberLikePagingResponse response = memberLikeQueryService.findSendLikesWithPaging(senderId, request);
        List<Long> result = response.memberLikes()
                .stream()
                .map(MemberLikeSimpleResponse::memberId)
                .toList();

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.memberLikes().size()).isEqualTo(9);
            softly.assertThat(response.nextPage()).isEqualTo(1);
            softly.assertThat(receivers.containsAll(result)).isEqualTo(true);
        });
    }

    @Test
    void 호감을_받은_회원을_조회한다() {
        // given
        Long receiverId = 1L;
        List<Long> senders = new ArrayList<>();
        for (long id = 2L; id <= 15L; id++) {
            memberLikeRepository.save(MemberLike.createWith(id, receiverId, "관심있어요"));
            senders.add(id);
        }
        PageRequest request = PageRequest.of(0, 9);

        // when
        MemberLikePagingResponse response = memberLikeQueryService.findReceivedLikesWithPaging(receiverId, request);
        List<Long> result = response.memberLikes()
                .stream()
                .map(MemberLikeSimpleResponse::memberId)
                .toList();

        // then
        assertSoftly(softly -> {
            softly.assertThat(response.memberLikes().size()).isEqualTo(9);
            softly.assertThat(response.nextPage()).isEqualTo(1);
            softly.assertThat(senders.containsAll(result)).isEqualTo(true);
        });
    }
}
