package com.atwoz.memberlike.infrastructure;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static com.atwoz.member.fixture.MemberFixture.일반_유저_생성;
import static com.atwoz.member.fixture.MemberProfileDtoFixture.회원_프로필_DTO_요청;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberLikeQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberLikeQueryRepository memberLikeQueryRepository;

    @Autowired
    private MemberLikeRepository memberLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void addMember() {
        Member member = 일반_유저_생성("회원 1", "000-0000-0001");
        member.updateWith("회원 1", 회원_프로필_DTO_요청());
        memberRepository.save(member);
    }

    @Test
    void 보낸_호감_목록_페이징_조회() {
        // given
        Long senderId = 1L;
        String like = "관심있어요";
        List<MemberLike> memberLikes = new ArrayList<>();

        for (long i = 2L; i <= 15L; i++) {
            Member member = 일반_유저_생성("회원 " + i, "000-0000-000" + i);
            member.updateWith("회원 " + i, 회원_프로필_DTO_요청());
            memberRepository.save(member);
            MemberLike memberLike = MemberLike.createWith(senderId, i, like);
            MemberLike savedMemberLike = memberLikeRepository.save(memberLike);
            memberLikes.add(savedMemberLike);
        }

        PageRequest pageRequest = PageRequest.of(0, 9);

        // when
        Page<MemberLikeSimpleResponse> found = memberLikeQueryRepository.findSendLikesWithPaging(senderId, pageRequest);

        // then
        List<Long> expected = extractMemberLikeReceiverIds(memberLikes, 9);
        List<Long> foundMembers = extractMemberLikeSimpleResponseIds(found, 9);
        
        assertSoftly(softly -> {
            softly.assertThat(found).hasSize(9);
            softly.assertThat(found.hasNext()).isTrue();
            softly.assertThat(foundMembers)
                    .isEqualTo(expected);
        });
    }

    private List<Long> extractMemberLikeReceiverIds(final List<MemberLike> memberLikes, final int limit) {
        return memberLikes.stream()
                .sorted(Comparator.comparing(MemberLike::getCreatedAt).reversed())
                .map(MemberLike::getReceiverId)
                .limit(limit)
                .toList();
    }

    private List<Long> extractMemberLikeSimpleResponseIds(final Page<MemberLikeSimpleResponse> responses, final int limit) {
        return responses.getContent().stream()
                .map(MemberLikeSimpleResponse::memberId)
                .limit(limit)
                .toList();
    }

    @Test
    void 받은_호감_목록_페이징_조회() {
        // given
        Long receiverId = 1L;
        String like = "관심있어요";
        List<MemberLike> memberLikes = new ArrayList<>();

        for (long i = 2L; i <= 15L; i++) {
            Member member = 일반_유저_생성("회원 " + i, "000-0000-000" + i);
            member.updateWith("회원 " + i, 회원_프로필_DTO_요청());
            memberRepository.save(member);
            MemberLike memberLike = MemberLike.createWith(i, receiverId, like);
            MemberLike savedMemberLike = memberLikeRepository.save(memberLike);
            memberLikes.add(savedMemberLike);
        }

        PageRequest pageRequest = PageRequest.of(0, 9);

        // when
        Page<MemberLikeSimpleResponse> found = memberLikeQueryRepository.findReceivedLikesWithPaging(receiverId, pageRequest);

        // then
        List<Long> expected = extractMemberLikeSenderIds(memberLikes, 9);
        List<Long> foundMembers = extractMemberLikeSimpleResponseIds(found, 9);

        assertSoftly(softly -> {
            softly.assertThat(found).hasSize(9);
            softly.assertThat(found.hasNext()).isTrue();
            softly.assertThat(foundMembers)
                    .isEqualTo(expected);
        });
    }

    private List<Long> extractMemberLikeSenderIds(final List<MemberLike> memberLikes, final int limit) {
        return memberLikes.stream()
                .sorted(Comparator.comparing(MemberLike::getCreatedAt).reversed())
                .map(MemberLike::getSenderId)
                .limit(limit)
                .toList();
    }
}
