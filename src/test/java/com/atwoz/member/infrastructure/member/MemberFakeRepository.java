package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.atwoz.member.fixture.MemberProfileDtoFixture.회원_프로핑_DTO_요청;
import static com.atwoz.member.fixture.MemberResponseFixture.회원_정보_응답서_요청;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFakeRepository implements MemberRepository {

    private final Map<Long, Member> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Optional<Member> findById(final Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Optional<Member> findByPhoneNumber(final String phoneNumber) {
        return map.values().stream()
                .filter(member -> phoneNumber.equals(member.getPhoneNumber()))
                .findAny();
    }

    @Override
    public Optional<Member> findByNickname(final String nickname) {
        return map.values().stream()
                .filter(member -> nickname.equals(member.getNickname()))
                .findAny();
    }

    @Override
    public MemberResponse findMemberWithProfile(final Long id) {
        Member member = map.get(id);

        return 회원_정보_응답서_요청(member);
    }

    @Override
    public Member save(final Member member) {
        Member savedMember = Member.builder()
                .id(id)
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .memberRole(member.getMemberRole())
                .memberProfile(MemberProfile.createWith(Gender.MALE.getName()))
                .build();

        savedMember.initializeWith(member.getNickname(), member.getRecommenderId(), 회원_프로핑_DTO_요청());
        map.put(id++, savedMember);

        return savedMember;
    }

    @Override
    public boolean existsByPhoneNumber(final String phoneNumber) {
        return map.values().stream()
                .anyMatch(member -> phoneNumber.equals(member.getPhoneNumber()));
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return map.values().stream()
                .anyMatch(member -> nickname.equals(member.getNickname()));
    }

    @Override
    public boolean existsById(final Long id) {
        return map.keySet().stream()
                .anyMatch(id::equals);
    }

    @Override
    public void deleteById(final Long id) {
        map.remove(id);
        this.id--;
    }
}
