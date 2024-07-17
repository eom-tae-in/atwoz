package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.member.domain.member.QMemberProfile.memberProfile;
import static com.atwoz.member.domain.member.profile.QProfile.profile;
import static com.atwoz.member.domain.member.profile.physical.QPhysicalProfile.physicalProfile;
import static com.atwoz.member.domain.selfintro.QSelfIntro.selfIntro;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class SelfIntroQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<SelfIntroResponse> findAllSelfIntroWithPaging(final Pageable pageable,
                                                              final Long memberId) {
        QueryResults<SelfIntroResponse> result = selectSelfIntroResponse()
                .from(member)
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .leftJoin(selfIntro).on(member.id.eq(selfIntro.memberId))
                .orderBy(selfIntro.createdAt.desc())
                .where(selfIntro.memberId.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private JPAQuery<SelfIntroResponse> selectSelfIntroResponse() {
        return jpaQueryFactory.select(
                constructor(SelfIntroResponse.class,
                        selfIntro.id,
                        selfIntro.content,
                        member.nickname,
                        profile.location.city,
                        physicalProfile.age,
                        physicalProfile.height
                )
        );
    }

    private Gender findMemberGender(final Long memberId) {
        return jpaQueryFactory.select(physicalProfile.gender)
                .from(member)
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .where(member.id.eq(memberId))
                .fetchFirst();
    }

    public Page<SelfIntroResponse> findAllSelfIntrosWithPagingAndFiltering(final Pageable pageable,
                                                                           final int minAge,
                                                                           final int maxAge,
                                                                           final boolean isOnlyOppositeGender,
                                                                           final List<String> cities,
                                                                           final Long memberId) {
        Gender memberGender = findMemberGender(memberId);
        QueryResults<SelfIntroResponse> result = selectSelfIntroResponse()
                .from(member)
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .leftJoin(selfIntro).on(member.id.eq(selfIntro.memberId))
                .orderBy(selfIntro.createdAt.desc())
                .where(
                        applyGenderCondition(isOnlyOppositeGender, memberGender),
                        ageBetween(minAge, maxAge),
                        locationIn(cities),
                        selfIntro.memberId.eq(memberId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression applyGenderCondition(final boolean isOnlyOppositeGender, final Gender gender) {
        if (!isOnlyOppositeGender) {
            return null;
        }

        return physicalProfile.gender.ne(gender);
    }

    private BooleanExpression ageBetween(final int minAge, final int maxAge) {
        return physicalProfile.age.goe(minAge)
                .and(physicalProfile.age.loe(maxAge));
    }

    private BooleanExpression locationIn(final List<String> cities) {
        return profile.location.city.in(cities);
    }
}
