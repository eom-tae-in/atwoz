package com.atwoz.profile.infrastructure;

import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.profile.application.dto.ProfileFilterRequest;
import com.atwoz.profile.domain.QUserHobbies;
import com.atwoz.profile.domain.UserHobby;
import com.atwoz.profile.domain.vo.Drink;
import com.atwoz.profile.domain.vo.Gender;
import com.atwoz.profile.domain.vo.ProfileAccessStatus;
import com.atwoz.profile.domain.vo.Religion;
import com.atwoz.profile.domain.vo.Smoke;
import com.atwoz.profile.infrastructure.dto.ProfileRecommendationResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.Group;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.memberlike.domain.QMemberLike.memberLike;
import static com.atwoz.profile.domain.QProfile.profile;
import static com.atwoz.profile.domain.QUserHobbies.userHobbies;
import static com.atwoz.profile.domain.QUserHobby.userHobby;
import static com.atwoz.profile.domain.vo.QPhysicalProfile.physicalProfile;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;

@RequiredArgsConstructor
@Repository
public class ProfileQueryRepository {

    private static final int REQUIRED_DIAMOND_MEMBER_FETCH_COUNT = 2;
    private static final int REQUIRED_GOLD_MEMBER_FETCH_COUNT = 3;

    private final JPAQueryFactory jpaQueryFactory;

    public List<ProfileRecommendationResponse> findTodayProfiles(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Gender foundGender = findGenderWithMemberId(memberId);

        List<ProfileRecommendationResponse> foundDiamondProfiles = findMemberProfiles(
                request,
                foundGender,
                MemberGrade.DIAMOND,
                REQUIRED_DIAMOND_MEMBER_FETCH_COUNT
        );

        int remainingFetchCount = REQUIRED_GOLD_MEMBER_FETCH_COUNT +
                REQUIRED_DIAMOND_MEMBER_FETCH_COUNT -
                foundDiamondProfiles.size();

        List<ProfileRecommendationResponse> foundGoldProfiles = findMemberProfiles(
                request,
                foundGender,
                MemberGrade.GOLD,
                remainingFetchCount
        );

        return mergeFoundProfiles(foundDiamondProfiles, foundGoldProfiles);
    }

    private List<ProfileRecommendationResponse> findMemberProfiles(
            final ProfileFilterRequest request,
            final Gender foundGender,
            final MemberGrade memberGrade,
            final int count
    ) {
        return jpaQueryFactory.select(constructor(ProfileRecommendationResponse.class,
                        profile.nickname,
                        profile.physicalProfile.age,
                        profile.location.city,
                        profile.location.sector,
                        profile.jobCode
                )).from(profile)
                .innerJoin(member).on(profile.memberId.eq(member.id))
                .where(
                        member.memberGrade.eq(memberGrade),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                ).limit(count)
                .fetch();
    }

    private List<ProfileRecommendationResponse> mergeFoundProfiles(
            final List<ProfileRecommendationResponse> diamondProfiles,
            final List<ProfileRecommendationResponse> goldProfiles
    ) {
        List<ProfileRecommendationResponse> profileRecommendationResponses = new ArrayList<>();
        profileRecommendationResponses.addAll(diamondProfiles);
        profileRecommendationResponses.addAll(goldProfiles);

        return profileRecommendationResponses;
    }

    // TODO: 우선 가장 인기 있는 회원 한 명만 조회되도록 설정함. 추후에 이야기 해서 상위 5%를 어떻게 설정할지 논의해봐야함.(쿼리 최적화도)
    public ProfileRecommendationResponse findProfileByPopularity(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Gender foundGender = findGenderWithMemberId(memberId);

        return jpaQueryFactory.select(constructor(ProfileRecommendationResponse.class,
                        profile.nickname,
                        profile.physicalProfile.age,
                        profile.location.city,
                        profile.location.sector,
                        profile.jobCode
                )).from(profile)
                .leftJoin(memberLike).on(profile.memberId.eq(memberLike.receiverId))
                .where(
                        memberLike.receiverId.ne(memberId),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                ).groupBy(memberLike.receiverId)
                .orderBy(memberLike.receiverId.count().desc())
                .fetchFirst();
    }

    public ProfileRecommendationResponse findProfileByTodayVisit(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Gender foundGender = findGenderWithMemberId(memberId);

        return jpaQueryFactory.select(
                        constructor(ProfileRecommendationResponse.class,
                                profile.nickname,
                                profile.physicalProfile.age,
                                profile.location.city,
                                profile.location.sector,
                                profile.jobCode
                        )).from(profile)
                .innerJoin(member).on(profile.memberId.eq(member.id))
                .where(
                        member.latestVisitDate.eq(LocalDate.now()),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                ).fetchFirst();
    }

    public ProfileRecommendationResponse findNearbyProfile(final ProfileFilterRequest request, final Long memberId) {
        Tuple tuple = findLocationAndGenderWithMemberId(memberId);
        String foundCity = tuple.get(profile.location.city);
        String foundSector = tuple.get(profile.location.sector);
        Gender foundGender = tuple.get(profile.physicalProfile.gender);

        return jpaQueryFactory.select(constructor(ProfileRecommendationResponse.class,
                        profile.nickname,
                        profile.physicalProfile.age,
                        profile.location.city,
                        profile.location.sector,
                        profile.jobCode
                )).from(profile)
                .where(
                        profile.location.city.eq(foundCity),
                        profile.location.sector.eq(foundSector)
                                .or(profile.location.sector.isNotNull()),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                ).fetchFirst();
    }

    private Tuple findLocationAndGenderWithMemberId(final Long memberId) {
        return jpaQueryFactory.select(
                        profile.location.city,
                        profile.location.sector,
                        profile.physicalProfile.gender
                ).from(profile)
                .where(profile.memberId.eq(memberId))
                .fetchFirst();
    }

    public ProfileRecommendationResponse findRecentProfile(final ProfileFilterRequest request, final Long memberId) {
        Gender foundGender = findGenderWithMemberId(memberId);

        return jpaQueryFactory.select(constructor(ProfileRecommendationResponse.class,
                        profile.nickname,
                        profile.physicalProfile.age,
                        profile.location.city,
                        profile.location.sector,
                        profile.jobCode
                )).from(profile)
                .where(
                        profile.memberId.ne(memberId),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                ).orderBy(profile.createdAt.desc())
                .fetchFirst();
    }

    public ProfileRecommendationResponse findProfileByReligion(
            final ProfileFilterRequest request,
            final Long memberId
    ) {
        Tuple tuple = findReligionAndGenderWithMemberId(memberId);
        Religion foundReligion = tuple.get(profile.religion);
        Gender foundGender = tuple.get(profile.physicalProfile.gender);

        return jpaQueryFactory.select(constructor(ProfileRecommendationResponse.class,
                        profile.nickname,
                        profile.physicalProfile.age,
                        profile.location.city,
                        profile.location.sector,
                        profile.jobCode
                )).from(profile)
                .where(
                        profile.religion.eq(foundReligion),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                ).fetchFirst();
    }

    private Tuple findReligionAndGenderWithMemberId(final Long memberId) {
        return jpaQueryFactory.select(profile.religion, profile.physicalProfile.gender)
                .from(profile)
                .where(profile.memberId.eq(memberId))
                .fetchFirst();
    }

    // TODO: 이거 잘 작동하는지 필히 확인해야함.
    public ProfileRecommendationResponse findProfileByHobbies(
            final ProfileFilterRequest request,
            final Long memberId
    ) {

        Group group = findUserHobbiesAndGenderWithMemberId(memberId);
        Gender foundGender = group.getOne(profile.physicalProfile.gender);
        List<UserHobby> foundUserHobbies = group.getList(userHobby);

        return jpaQueryFactory.select(constructor(ProfileRecommendationResponse.class,
                        profile.nickname,
                        profile.physicalProfile.age,
                        profile.location.city,
                        profile.location.sector,
                        profile.jobCode
                )).from(profile)
                .leftJoin(profile.userHobbies, QUserHobbies.userHobbies)
                .leftJoin(QUserHobbies.userHobbies.hobbies, userHobby)
                .where(
                        userHobby.in(foundUserHobbies),
                        applyBaseRecommendProfileCondition(foundGender),
                        applyFilterConditions(request)
                )
                .fetchFirst();
    }

    private Group findUserHobbiesAndGenderWithMemberId(final Long memberId) {
        return jpaQueryFactory.select(profile.physicalProfile.gender, userHobby)
                .from(profile)
                .innerJoin(profile.userHobbies, userHobbies)
                .innerJoin(userHobbies.hobbies, userHobby)
                .where(profile.memberId.eq(memberId))
                .transform(groupBy(profile.id)
                        .list(profile.physicalProfile.gender,
                                list(userHobby)
                        )
                ).get(0);
    }

    private Gender findGenderWithMemberId(final Long memberId) {
        return jpaQueryFactory.select(profile.physicalProfile.gender)
                .from(profile)
                .where(profile.memberId.eq(memberId))
                .fetchFirst();
    }


    private BooleanBuilder applyBaseRecommendProfileCondition(final Gender foundGender) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(profile.physicalProfile.gender.ne(foundGender));
        booleanBuilder.and(profile.profileAccessStatusByAdmin.eq(ProfileAccessStatus.PUBLIC));
        booleanBuilder.and(profile.profileAccessStatusByUser.eq(ProfileAccessStatus.PUBLIC));

        return booleanBuilder;
    }

    private BooleanBuilder applyFilterConditions(final ProfileFilterRequest request) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(addFilterCondition(
                request.minAge(),
                Objects::isNull,
                physicalProfile.age::goe
        ));

        booleanBuilder.and(addFilterCondition(
                request.maxAge(),
                Objects::isNull,
                physicalProfile.age::loe
        ));

        booleanBuilder.and(addFilterCondition(
                Smoke.findByName(request.smoke()),
                Objects::isNull,
                profile.smoke::eq
        ));

        booleanBuilder.and(addFilterCondition(
                Drink.findByName(request.drink()),
                Objects::isNull,
                profile.drink::eq
        ));

        booleanBuilder.and(addFilterCondition(
                Religion.findByName(request.religion()),
                Objects::isNull,
                profile.religion::eq
        ));

        booleanBuilder.and(addFilterCondition(
                request.hobbyCode(),
                Objects::isNull,
                userHobby.hobbyCode::eq
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
