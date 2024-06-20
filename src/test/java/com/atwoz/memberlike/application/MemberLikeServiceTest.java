package com.atwoz.memberlike.application;

import com.atwoz.global.event.Events;
import com.atwoz.member.application.member.event.ValidatedMemberExistenceEvent;
import com.atwoz.memberlike.application.dto.MemberLikeCreateRequest;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.exception.exceptions.InvalidMemberLikeException;
import com.atwoz.memberlike.infrastructure.MemberLikeFakeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.event.RecordApplicationEvents;

import static com.atwoz.memberlike.fixture.MemberLikeFixture.보낸_지_31일_된_호감_생성;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.보낸_지_사흘_된_호감_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@RecordApplicationEvents
class MemberLikeServiceTest {

    private MemberLikeService memberLikeService;
    private MemberLikeRepository memberLikeRepository;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void init() {
        memberLikeRepository = new MemberLikeFakeRepository();
        memberLikeService = new MemberLikeService(memberLikeRepository);
        eventPublisher = mock(ApplicationEventPublisher.class);
        Events.setPublisher(eventPublisher);
    }

    @Test
    void 호감을_보낸다() {
        // given
        Long memberId = 1L;
        MemberLikeCreateRequest request = new MemberLikeCreateRequest(2L, "관심있어요");
        doNothing().when(eventPublisher).publishEvent(any(ValidatedMemberExistenceEvent.class));

        // when
        memberLikeService.sendLike(memberId, request);

        // then
        assertThat(memberLikeRepository.isAlreadyExisted(memberId, request.memberId()))
                .isEqualTo(true);
    }

    @Test
    void 자기_자신에게_호감을_보내면_예외가_발생한다() {
        // given
        Long memberId = 1L;
        MemberLikeCreateRequest request = new MemberLikeCreateRequest(memberId, "관심있어요");

        // when & then
        assertThatThrownBy(() -> memberLikeService.sendLike(memberId, request))
                .isInstanceOf(InvalidMemberLikeException.class);
    }

    @Test
    void 호감을_보낸_지_30일이_지나도_응답이_없는_호감은_삭제된다() {
        // given
        Long senderId = 1L;
        Long receiverId = 2L;
        memberLikeRepository.save(보낸_지_31일_된_호감_생성());

        // when
        memberLikeService.deleteExpiredLikes();

        // then
        assertThat(memberLikeRepository.isAlreadyExisted(senderId, receiverId))
                .isEqualTo(false);
    }

    @Test
    void 호감을_보낸_지_48시간이_지나면_최근_여부가_변경된다() {
        // given
        Long senderId = 1L;
        int index = 0;
        memberLikeRepository.save(보낸_지_사흘_된_호감_생성());

        // when
        memberLikeService.endRecentByTimeExpired();

        // then
        Page<MemberLikeSimpleResponse> response = memberLikeRepository.findSendLikesWithPaging(senderId, PageRequest.of(0, 1));
        MemberLikeSimpleResponse result = response.getContent()
                .get(index);
        assertThat(result.isRecent()).isFalse();
    }
}
