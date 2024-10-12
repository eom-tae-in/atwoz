package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_계정_상태_조회_응답_픽스처.회원_계정_상태_조회_응답_생성_회원계정상태;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_조회_응답_픽스처.회원_연락처_정보_조회_응답_생성_연락처;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_푸시_알림_조회_응답_픽스처.회원_푸시_알림_조회_응답_생성_회원푸시알림목록;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFakeRepository implements MemberRepository {

    private final Map<Long, Member> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Member save(final Member member) {
        Member savedMember = Member.builder()
                .id(id)
                .latestVisitDate(member.getLatestVisitDate())
                .memberGrade(member.getMemberGrade())
                .memberAccountStatus(member.getMemberAccountStatus())
                .contact(member.getContact())
                .memberPushNotifications(member.getMemberPushNotifications())
                .blockedMembers(member.getBlockedMembers())
                .build();

        map.put(id++, savedMember);

        return savedMember;
    }

    @Override
    public Optional<Member> findById(final Long memberId) {
        return Optional.ofNullable(map.get(memberId));
    }

    @Override
    public MemberNotificationsResponse findMemberNotifications(final Long memberId) {
        return map.values()
                .stream()
                .filter(member -> member.getId().equals(memberId))
                .findAny()
                .map(member -> 회원_푸시_알림_조회_응답_생성_회원푸시알림목록(member.getMemberPushNotifications()))
                .orElse(null);
    }

    @Override
    public MemberAccountStatusResponse findMemberAccountStatus(final Long memberId) {
        return map.values()
                .stream()
                .filter(member -> member.getId().equals(memberId))
                .findAny()
                .map(member -> 회원_계정_상태_조회_응답_생성_회원계정상태(member.getMemberAccountStatus()))
                .orElse(null);
    }

    @Override
    public MemberContactInfoResponse findMemberContactInfo(final Long memberId) {
        return map.values()
                .stream()
                .filter(member -> member.getId().equals(memberId))
                .findAny()
                .map(member -> 회원_연락처_정보_조회_응답_생성_연락처(member.getContact()))
                .orElse(null);
    }

    @Override
    public boolean existsByContact(final Contact contact) {
        return map.values()
                .stream()
                .anyMatch(member -> member.getContact().equals(contact));
    }

    @Override
    public boolean existsById(final Long memberId) {
        return map.containsKey(memberId);
    }

    @Override
    public void deleteById(final Long memberId) {
        map.remove(memberId);
    }

    public List<Member> getMembers() {
        return map.values()
                .stream()
                .toList();
    }
}
