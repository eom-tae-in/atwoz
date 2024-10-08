//package com.atwoz.memberlike.infrastructure;
//
//import com.atwoz.helper.IntegrationHelper;
//import com.atwoz.member.domain.member.Member;
//import com.atwoz.member.domain.member.MemberRepository;
//import com.atwoz.global.fixture.PhoneNumberGenerator;
//import com.atwoz.memberlike.domain.MemberLike;
//import com.atwoz.memberlike.domain.MemberLikeRepository;
//import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import static com.atwoz.member.fixture.member.회원_픽스처.회원_생성_전화번호;
//import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_날짜_주입;
//import static org.assertj.core.api.SoftAssertions.assertSoftly;
//
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class MemberLikeQueryRepositoryTest extends IntegrationHelper {
//
//    @Autowired
//    private MemberLikeQueryRepository memberLikeQueryRepository;
//
//    @Autowired
//    private MemberLikeRepository memberLikeRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private PhoneNumberGenerator phoneNumberGenerator;
//
//    @BeforeEach
//    void addMember() {
//        phoneNumberGenerator = new PhoneNumberGenerator();
//        memberRepository.save(회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber()));
//    }
//
//    @Test
//    void 보낸_호감_목록_페이징_조회() {
//        // given
//        Long senderId = 1L;
//        List<MemberLike> memberLikes = new ArrayList<>();
//
//        for (long i = 2L; i <= 15L; i++) {
//            Member member = 회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber());
//            memberRepository.save(member);
//            MemberLike memberLike = 호감_생성_id_날짜_주입(senderId, i, (int) i);
//            memberLikeRepository.save(memberLike);
//            memberLikes.add(memberLike);
//        }
//
//        PageRequest pageRequest = PageRequest.of(0, 9);
//
//        // when
//        Page<MemberLikeSimpleResponse> found = memberLikeQueryRepository.findSendLikesWithPaging(senderId, pageRequest);
//
//        // then
//        List<Long> expected = extractMemberLikeReceiverIds(memberLikes, 9);
//        List<Long> foundMembers = extractMemberLikeSimpleResponseIds(found);
//
//        assertSoftly(softly -> {
//            softly.assertThat(found).hasSize(9);
//            softly.assertThat(found.hasNext()).isTrue();
//            softly.assertThat(foundMembers).isEqualTo(expected);
//        });
//    }
//
//    private List<Long> extractMemberLikeReceiverIds(final List<MemberLike> memberLikes, final int limit) {
//        List<Long> ids = memberLikes.subList(memberLikes.size() - limit, memberLikes.size())
//                .stream()
//                .map(MemberLike::getReceiverId)
//                .collect(Collectors.toList());
//        Collections.reverse(ids);
//        return ids;
//    }
//
//    private List<Long> extractMemberLikeSimpleResponseIds(final Page<MemberLikeSimpleResponse> responses) {
//        return responses.getContent().stream()
//                .map(MemberLikeSimpleResponse::memberId)
//                .toList();
//    }
//
//    @Test
//    void 받은_호감_목록_페이징_조회() {
//        // given
//        Long receiverId = 1L;
//        List<MemberLike> memberLikes = new ArrayList<>();
//
//        for (long i = 2L; i <= 15L; i++) {
//            Member member = 회원_생성_전화번호(phoneNumberGenerator.generatePhoneNumber());
//            memberRepository.save(member);
//            MemberLike memberLike = 호감_생성_id_날짜_주입(i, receiverId, (int) i);
//            memberLikeRepository.save(memberLike);
//            memberLikes.add(memberLike);
//        }
//
//        PageRequest pageRequest = PageRequest.of(0, 9);
//
//        // when
//        Page<MemberLikeSimpleResponse> found = memberLikeQueryRepository.findReceivedLikesWithPaging(receiverId,
//                pageRequest);
//
//        // then
//        List<Long> expected = extractMemberLikeSenderIds(memberLikes, 9);
//        List<Long> foundMembers = extractMemberLikeSimpleResponseIds(found);
//
//        assertSoftly(softly -> {
//            softly.assertThat(found).hasSize(9);
//            softly.assertThat(found.hasNext()).isTrue();
//            softly.assertThat(foundMembers).isEqualTo(expected);
//        });
//    }
//
//    private List<Long> extractMemberLikeSenderIds(final List<MemberLike> memberLikes, final int limit) {
//        List<Long> ids = memberLikes.subList(memberLikes.size() - limit, memberLikes.size())
//                .stream()
//                .map(MemberLike::getSenderId)
//                .collect(Collectors.toList());
//        Collections.reverse(ids);
//        return ids;
//    }
//}
