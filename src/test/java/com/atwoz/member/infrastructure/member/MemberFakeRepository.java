package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemberFakeRepository implements MemberRepository {

    private final Map<Long, Member> map = new HashMap<>();
    private Long id = 0L;

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
    public Member save(final Member member) {
        Member saved = Member.builder()
                .id(id)
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .memberRole(member.getMemberRole())
                .build();

        map.put(id++, member);

        return saved;
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
