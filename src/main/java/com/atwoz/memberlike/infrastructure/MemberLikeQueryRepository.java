package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.vo.Location;
import com.atwoz.profile.domain.vo.PhysicalProfile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.memberlike.domain.QMemberLike.memberLike;
import static com.atwoz.profile.domain.QProfile.profile;

@RequiredArgsConstructor
@Repository
public class MemberLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<MemberLikeSimpleResponse> findSendLikesWithPaging(final Long senderId, final Pageable pageable) {
        List<Long> foundReceivers = findReceivers(senderId, pageable);
        List<Profile> receiverProfiles = foundReceivers.stream()
                .map(this::findProfile)
                .toList();

        List<MemberLikeSimpleResponse> responses = receiverProfiles.stream()
                .map(receiver -> collectReceiverResponse(senderId, receiver))
                .toList();

        return new PageImpl<>(responses, pageable, foundReceivers.size());
    }

    private List<Long> findReceivers(final Long senderId, final Pageable pageable) {
        return jpaQueryFactory.select(memberLike.receiverId)
                .from(memberLike)
                .innerJoin(member)
                .on(memberLike.receiverId.eq(member.id))
                .where(memberLike.senderId.eq(senderId))
                .orderBy(memberLike.createdAt.desc(), memberLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Profile findProfile(final Long memberId) {
        return jpaQueryFactory.selectFrom(profile)
                .where(profile.memberId.eq(memberId))
                .fetchFirst();
    }

    private MemberLikeSimpleResponse collectReceiverResponse(final Long senderId, final Profile receiverProfile) {
        Location location = receiverProfile.getLocation();
        PhysicalProfile physicalProfile = receiverProfile.getPhysicalProfile();
        MemberLike memberLike = findMemberLike(senderId, receiverProfile.getId());

        return new MemberLikeSimpleResponse(
                receiverProfile.getMemberId(),
                receiverProfile.getNickname(),
                location.getCity(),
                location.getSector(),
                physicalProfile.getAge(),
                memberLike.getLikeIcon().getName(),
                memberLike.getIsRecent()
        );
    }

    private MemberLike findMemberLike(final Long senderId, final Long receiverId) {
        return jpaQueryFactory.selectFrom(memberLike)
                .where(memberLike.senderId.eq(senderId), memberLike.receiverId.eq(receiverId))
                .fetchOne();
    }

    public Page<MemberLikeSimpleResponse> findReceivedLikesWithPaging(final Long receiverId, final Pageable pageable) {
        List<Long> foundSenders = findSenders(receiverId, pageable);
        List<Profile> senders = foundSenders.stream()
                .map(this::findProfile)
                .toList();

        List<MemberLikeSimpleResponse> responses = senders.stream()
                .map(sender -> collectSenderResponse(receiverId, sender))
                .toList();

        return new PageImpl<>(responses, pageable, foundSenders.size());
    }

    private List<Long> findSenders(final Long receiverId, final Pageable pageable) {
        return jpaQueryFactory.select(memberLike.senderId)
                .from(memberLike)
                .innerJoin(member)
                .on(memberLike.senderId.eq(member.id))
                .where(memberLike.receiverId.eq(receiverId))
                .orderBy(memberLike.createdAt.desc(), memberLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private MemberLikeSimpleResponse collectSenderResponse(final Long receiverId, final Profile senderProfile) {
        Location location = senderProfile.getLocation();
        PhysicalProfile physicalProfile = senderProfile.getPhysicalProfile();
        MemberLike memberLike = findMemberLike(senderProfile.getId(), receiverId);

        return new MemberLikeSimpleResponse(
                senderProfile.getId(),
                senderProfile.getNickname(),
                location.getCity(),
                location.getSector(),
                physicalProfile.getAge(),
                memberLike.getLikeIcon().getName(),
                memberLike.getIsRecent()
        );
    }
}
