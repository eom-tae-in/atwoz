package com.atwoz.selfintro.infrastructure;

import com.atwoz.profile.domain.vo.Gender;
import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.atwoz.profile.domain.QProfile.profile;
import static com.atwoz.selfintro.domain.QSelfIntro.selfIntro;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class SelfIntroQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<SelfIntroQueryResponse> findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final SelfIntroFilterRequest request,
            final Long memberId
    ) {
        Gender foundGender = findGender(memberId);

        List<SelfIntroQueryResponse> fetch = jpaQueryFactory.select(constructor(SelfIntroQueryResponse.class,
                        selfIntro.id,
                        selfIntro.content,
                        profile.nickname,
                        profile.location.city,
                        profile.physicalProfile.age,
                        profile.physicalProfile.height
                )).from(selfIntro)
                .innerJoin(profile).on(selfIntro.memberId.eq(profile.memberId))
                .orderBy(selfIntro.createdAt.desc())
                .where(applyFilterConditions(request, foundGender))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(selfIntro.count())
                .from(selfIntro)
                .innerJoin(profile).on(selfIntro.memberId.eq(profile.memberId))
                .where(applyFilterConditions(request, foundGender));

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

    private Gender findGender(final Long memberId) {
        return jpaQueryFactory.select(profile.physicalProfile.gender)
                .from(profile)
                .where(profile.memberId.eq(memberId))
                .fetchFirst();
    }


    private BooleanBuilder applyFilterConditions(final SelfIntroFilterRequest request, final Gender foundGender) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(addFilterCondition(
                request.isOnlyOppositeGender(),
                Boolean.FALSE::equals,
                value -> profile.physicalProfile.gender.ne(foundGender)
        ));

        booleanBuilder.and(addFilterCondition(
                request.minAge(),
                Objects::isNull,
                profile.physicalProfile.age::goe
        ));

        booleanBuilder.and(addFilterCondition(
                request.maxAge(),
                Objects::isNull,
                profile.physicalProfile.age::loe
        ));

        booleanBuilder.and(addFilterCondition(
                request.cities(),
                cities -> cities == null || cities.isEmpty(),
                profile.location.city::in
        ));

        return booleanBuilder;
    }

    private <T> BooleanExpression addFilterCondition(
            final T value,
            final Predicate<T> invalidCondition,
            final Function<T, BooleanExpression> filterCondition
    ) {
        if (invalidCondition.test(value)) {
            return null;
        }

        return filterCondition.apply(value);
    }
}
