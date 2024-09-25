package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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

    public Page<SelfIntroResponse> findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final Integer minAge,
            final Integer maxAge,
            final Boolean isOnlyOppositeGender,
            final List<String> cities,
            final Long memberId
    ) {
        Gender memberGender = findMemberGender(memberId);
        List<SelfIntroResponse> fetch = selectSelfIntroResponse()
                .from(selfIntro)
                .leftJoin(member).on(selfIntro.memberId.eq(member.id))
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .orderBy(selfIntro.createdAt.desc())
                .where(
                        applyGenderCondition(isOnlyOppositeGender, memberGender),
                        applyAgeCondition(minAge, maxAge),
                        applyLocationCondition(cities)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(selfIntro.count())
                .from(selfIntro)
                .leftJoin(member).on(selfIntro.memberId.eq(member.id))
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .where(
                        applyGenderCondition(isOnlyOppositeGender, memberGender),
                        applyAgeCondition(minAge, maxAge),
                        applyLocationCondition(cities)
                );

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

    private BooleanExpression applyGenderCondition(final Boolean isOnlyOppositeGender, final Gender gender) {
        if (isOnlyOppositeGender == null || !isOnlyOppositeGender) {
            return null;
        }

        return physicalProfile.gender.ne(gender);
    }

    private BooleanBuilder applyAgeCondition(final Integer minAge, final Integer maxAge) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (minAge != null) {
            booleanBuilder.and(physicalProfile.age.goe(minAge));
        }
        if (maxAge != null) {
            booleanBuilder.and(physicalProfile.age.loe(maxAge));
        }

        return booleanBuilder;
    }

    private BooleanExpression applyLocationCondition(final List<String> cities) {
        if (cities.isEmpty()) {
            return null;
        }

        return profile.location.city.in(cities);
    }
}
