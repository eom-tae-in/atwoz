package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.memberlike.domain.QMemberLike.memberLike;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class MemberLikeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<MemberLikeSimpleResponse> findSendLikesWithPaging(final Long senderId, final Pageable pageable) {
        QueryResults<MemberLikeSimpleResponse> result = jpaQueryFactory.select(
                        constructor(MemberLikeSimpleResponse.class,
                                member.id,
                                member.nickname,
                                member.memberProfile.profile.location.city,
                                member.memberProfile.profile.location.sector,
                                member.memberProfile.profile.physicalProfile.age,
                                memberLike.likeIcon,
                                memberLike.isRecent)
                ).from(memberLike)
                .innerJoin(member)
                .on(memberLike.receiverId.eq(member.id))
                .where(memberLike.senderId.eq(senderId))
                .orderBy(memberLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    public Page<MemberLikeSimpleResponse> findReceivedLikesWithPaging(final Long receiverId, final Pageable pageable) {
        QueryResults<MemberLikeSimpleResponse> result = jpaQueryFactory.select(
                        constructor(MemberLikeSimpleResponse.class,
                                member.id,
                                member.nickname,
                                member.memberProfile.profile.location.city,
                                member.memberProfile.profile.location.sector,
                                member.memberProfile.profile.physicalProfile.age,
                                memberLike.likeIcon,
                                memberLike.isRecent)
                ).from(memberLike)
                .innerJoin(member)
                .on(memberLike.senderId.eq(member.id))
                .where(memberLike.receiverId.eq(receiverId))
                .orderBy(memberLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
