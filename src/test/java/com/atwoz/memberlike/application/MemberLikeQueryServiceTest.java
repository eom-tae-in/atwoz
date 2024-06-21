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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_날짜_주입;
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
    void 나에게_호감을_보낸_회원을_조회한다() {
        // given
        Long memberId = 1L;
        List<MemberLike> memberLikes = new ArrayList<>();
        for (long id = 2L; id <= 15L; id++) {
            MemberLike memberLike = 호감_생성_id_날짜_주입(id, memberId, (int) id);
            memberLikeRepository.save(memberLike);
            memberLikes.add(memberLike);
        }
        PageRequest request = PageRequest.of(0, 9);

        // when
        MemberLikePagingResponse response = memberLikeQueryService.findReceivedLikesWithPaging(memberId, request);
        List<Long> senders = extractSenders(memberLikes, 9);
        List<Long> result = response.memberLikes()
                .stream()
                .map(MemberLikeSimpleResponse::memberId)
                .toList();
        System.out.println("senders = " + senders);
        System.out.println("result = " + result);
        // then
        assertSoftly(softly -> {
            softly.assertThat(response.memberLikes().size()).isEqualTo(9);
            softly.assertThat(response.nextPage()).isEqualTo(1);
            softly.assertThat(senders).isEqualTo(result);
        });
    }

    private List<Long> extractSenders(final List<MemberLike> memberLikes, final int limit) {
        List<Long> ids = memberLikes.subList(memberLikes.size() - limit, memberLikes.size())
                .stream()
                .map(MemberLike::getSenderId)
                .collect(Collectors.toList());
        Collections.reverse(ids);
        return ids;
    }

    @Test
    void 나에게_호감을_받은_회원을_조회한다() {
        // given
        Long memberId = 1L;
        List<MemberLike> memberLikes = new ArrayList<>();
        for (long id = 2L; id <= 15L; id++) {
            MemberLike memberLike = 호감_생성_id_날짜_주입(memberId, id, (int) id);
            memberLikeRepository.save(memberLike);
            memberLikes.add(memberLike);
        }
        PageRequest request = PageRequest.of(0, 9);

        // when
        MemberLikePagingResponse response = memberLikeQueryService.findSendLikesWithPaging(memberId, request);
        List<Long> receivers = extractReceivers(memberLikes, 9);
        List<Long> result = response.memberLikes()
                .stream()
                .map(MemberLikeSimpleResponse::memberId)
                .toList();
        System.out.println("receivers = " + receivers);
        System.out.println("result = " + result);
        // then
        assertSoftly(softly -> {
            softly.assertThat(response.memberLikes().size()).isEqualTo(9);
            softly.assertThat(response.nextPage()).isEqualTo(1);
            softly.assertThat(receivers).isEqualTo(result);
        });
    }

    private List<Long> extractReceivers(final List<MemberLike> memberLikes, final int limit) {
        List<Long> ids = memberLikes.subList(memberLikes.size() - limit, memberLikes.size())
                .stream()
                .map(MemberLike::getReceiverId)
                .collect(Collectors.toList());
        Collections.reverse(ids);
        return ids;
    }
}
