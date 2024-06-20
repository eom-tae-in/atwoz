package com.atwoz.memberlike.infrastructure;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.member.domain.member.QMemberProfile.memberProfile;
import static com.atwoz.member.domain.member.profile.QProfile.profile;
import static com.atwoz.member.domain.member.profile.physical.QPhysicalProfile.physicalProfile;
import static com.atwoz.memberlike.domain.QMemberLike.memberLike;

@RequiredArgsConstructor
@Repository
public class MemberLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<MemberLikeSimpleResponse> findSendLikesWithPaging(final Long senderId, final Pageable pageable) {
        QueryResults<Long> results = findReceivers(senderId, pageable);
        List<Member> receivers = results.getResults()
                .stream()
                .map(this::findMember)
                .toList();
        List<MemberLikeSimpleResponse> responses = receivers.stream()
                    .map(receiver -> collectReceiverResponse(senderId, receiver))
                    .toList();

            return new PageImpl<>(responses, pageable, results.getTotal());
        }

    private QueryResults<Long> findReceivers(final Long senderId, final Pageable pageable) {
        return jpaQueryFactory.select(memberLike.receiverId)
                .from(memberLike)
                .innerJoin(member)
                .on(memberLike.receiverId.eq(member.id))
                .where(memberLike.senderId.eq(senderId))
                .orderBy(memberLike.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    private Member findMember(final Long memberId) {
        return jpaQueryFactory.selectFrom(member)
                .innerJoin(member.memberProfile, memberProfile)
                .fetchJoin()
                .innerJoin(memberProfile.profile, profile)
                .fetchJoin()
                .innerJoin(profile.physicalProfile, physicalProfile)
                .fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    private MemberLikeSimpleResponse collectReceiverResponse(final Long senderId, final Member receiver) {
        Location location = getProfile(receiver).getLocation();
        Integer age = getAge(getProfile(receiver));
        MemberLike memberLike = findMemberLike(senderId, receiver.getId());
        return new MemberLikeSimpleResponse(
                receiver.getId(),
                receiver.getNickname(),
                location.getCity(),
                location.getSector(),
                age,
                memberLike.getLikeIcon().getName(),
                memberLike.getIsRecent()
        );
    }

    private Profile getProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile();
    }

    private Integer getAge(final Profile profile) {
        return profile.getPhysicalProfile()
                .getAge();
    }

    private MemberLike findMemberLike(final Long senderId, final Long receiverId) {
        return jpaQueryFactory.selectFrom(memberLike)
                .where(memberLike.senderId.eq(senderId), memberLike.receiverId.eq(receiverId))
                .fetchOne();
    }
    
    public Page<MemberLikeSimpleResponse> findReceivedLikesWithPaging(final Long receiverId, final Pageable pageable) {
        QueryResults<Long> results = findSenders(receiverId, pageable);
        List<Member> senders = results.getResults()
                .stream()
                .map(this::findMember)
                .toList();
        List<MemberLikeSimpleResponse> responses = senders.stream()
                .map(receiver -> collectSenderResponse(receiverId, receiver))
                .toList();

        return new PageImpl<>(responses, pageable, results.getTotal());
    }

    private QueryResults<Long> findSenders(final Long receiverId, final Pageable pageable) {
        return jpaQueryFactory.select(memberLike.senderId)
                .from(memberLike)
                .innerJoin(member)
                .on(memberLike.senderId.eq(member.id))
                .where(memberLike.receiverId.eq(receiverId))
                .orderBy(memberLike.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    private MemberLikeSimpleResponse collectSenderResponse(final Long receiverId, final Member sender) {
        Location location = getProfile(sender).getLocation();
        Integer age = getAge(getProfile(sender));
        MemberLike memberLike = findMemberLike(sender.getId(), receiverId);
        return new MemberLikeSimpleResponse(
                sender.getId(),
                sender.getNickname(),
                location.getCity(),
                location.getSector(),
                age,
                memberLike.getLikeIcon().getName(),
                memberLike.getIsRecent()
        );
    }
}
