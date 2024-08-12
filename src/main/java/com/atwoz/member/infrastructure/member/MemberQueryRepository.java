package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.member.domain.member.QMemberProfile.memberProfile;
import static com.atwoz.member.domain.member.profile.QHobby.hobby;
import static com.atwoz.member.domain.member.profile.QMemberHobbies.memberHobbies;
import static com.atwoz.member.domain.member.profile.QMemberHobby.memberHobby;
import static com.atwoz.member.domain.member.profile.QMemberStyle.memberStyle;
import static com.atwoz.member.domain.member.profile.QMemberStyles.memberStyles;
import static com.atwoz.member.domain.member.profile.QProfile.profile;
import static com.atwoz.member.domain.member.profile.QStyle.style;
import static com.atwoz.member.domain.member.profile.physical.QPhysicalProfile.physicalProfile;
import static com.atwoz.memberlike.domain.QMemberLike.memberLike;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberResponse findMemberWithProfile(final Long memberId) {
        Member member = findMember(memberId);
        List<Hobby> hobbies = getHobbies(getMemberHobbiesId(member));
        List<Style> styles = getStyles(getMemberStylesId(member));

        return MemberResponse.of(member, hobbies, styles);
    }

    private Member findMember(final Long memberId) {
        return jpaQueryFactory.selectFrom(member)
                .leftJoin(member.memberProfile, memberProfile)
                .fetchJoin()
                .leftJoin(memberProfile.profile, profile)
                .fetchJoin()
                .leftJoin(profile.physicalProfile, physicalProfile)
                .fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    private Profile getProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile();
    }

    private PhysicalProfile getPhysicalProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getPhysicalProfile();
    }

    private Long getMemberHobbiesId(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getMemberHobbies()
                .getId();
    }

    private Long getMemberStylesId(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getMemberStyles()
                .getId();
    }

    private List<Hobby> getHobbies(final Long memberHobbiesId) {
        return jpaQueryFactory.select(hobby)
                .from(memberHobbies)
                .innerJoin(memberHobbies.hobbies, memberHobby)
                .innerJoin(memberHobby.hobby, hobby)
                .where(memberHobbies.id.eq(memberHobbiesId))
                .fetch();
    }

    private List<Style> getStyles(final Long memberStylesId) {
        return jpaQueryFactory.select(style)
                .from(memberStyles)
                .innerJoin(memberStyles.styles, memberStyle)
                .where(memberStyles.id.eq(memberStylesId))
                .fetch();
    }

    public ProfileResponse findProfileByPopularity(final InternalProfileFilterRequest request,
                                                   final Long memberId) {
        Gender foundGender = findMemberGender(memberId);
        JPAQuery<ProfileResponse> profileFindQuery = buildProfileFindQuery(request, foundGender);

        return profileFindQuery.where(member.id.eq(findMostPopularMemberId(memberId)))
                .fetchFirst();
    }

    private JPAQuery<ProfileResponse> buildProfileFindQuery(final InternalProfileFilterRequest request,
                                                            final Gender gender) {
        JPAQuery<ProfileResponse> profileFindQuery = jpaQueryFactory.select(getProfileResponse())
                .from(member)
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .where(physicalProfile.gender.ne(gender),
                        profile.profileAccessStatus.eq(ProfileAccessStatus.PUBLIC),
                        memberProfile.profileAccessStatus.eq(ProfileAccessStatus.PUBLIC),
                        applyFilterCondition(request));
        if (request.hobbyCode() != null) {
            List<Long> memberHobbiesIds = findMemberHobbiesIdByHobbyCode(request.hobbyCode());
            profileFindQuery.where(profile.memberHobbies.id.in(memberHobbiesIds));
        }

        return profileFindQuery;
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

    private ConstructorExpression<ProfileResponse> getProfileResponse() {
        return constructor(ProfileResponse.class,
                member.nickname,
                physicalProfile.age,
                profile.location.city,
                profile.location.sector,
                profile.job
        );
    }

    private BooleanBuilder applyFilterCondition(final InternalProfileFilterRequest request) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        addFilterCondition(booleanBuilder, request.minAge(), physicalProfile.age::goe);
        addFilterCondition(booleanBuilder, request.maxAge(), physicalProfile.age::loe);
        addFilterCondition(booleanBuilder, request.smoke(), profile.smoke::eq);
        addFilterCondition(booleanBuilder, request.drink(), profile.drink::eq);
        addFilterCondition(booleanBuilder, request.religion(), profile.religion::eq);
        if (!request.cities().isEmpty()) {
            booleanBuilder.and(profile.location.city.in(request.cities()));
        }

        return booleanBuilder;
    }

    private <T> void addFilterCondition(final BooleanBuilder booleanBuilder,
                                        final T value,
                                        final Function<T, BooleanExpression> expressionFunction) {
        if (value != null) {
            booleanBuilder.and(expressionFunction.apply(value));
        }
    }

    private List<Long> findMemberHobbiesIdByHobbyCode(final String hobbyCode) {
        return jpaQueryFactory.select(memberHobbies.id)
                .from(memberHobbies)
                .leftJoin(memberHobbies.hobbies, memberHobby)
                .leftJoin(memberHobby.hobby, hobby)
                .where(hobby.code.eq(hobbyCode))
                .fetch();
    }

    private JPQLQuery<Long> findMostPopularMemberId(final Long memberId) {
        return JPAExpressions.select(memberLike.receiverId)
                .from(memberLike)
                .where(memberLike.receiverId.ne(memberId))
                .groupBy(memberLike.receiverId)
                .orderBy(memberLike.receiverId.count().desc())
                .limit(1);
    }

    public ProfileResponse findProfileByTodayVisit(final InternalProfileFilterRequest request,
                                                   final Long memberId) {
        Gender foundGender = findMemberGender(memberId);
        JPAQuery<ProfileResponse> profileFindQuery = buildProfileFindQuery(request, foundGender);

        return profileFindQuery
                .where(member.latestVisitDate.eq(LocalDate.now()))
                .fetchFirst();
    }

    public ProfileResponse findNearbyProfile(final InternalProfileFilterRequest request,
                                             final Long memberId) {
        Member foundMember = findMember(memberId);
        JPAQuery<ProfileResponse> profileFindQuery = buildProfileFindQuery(request,
                getPhysicalProfile(foundMember).getGender());

        return profileFindQuery.where(
                        profile.location.city.eq(getProfile(foundMember).getLocation().getCity()),
                        profile.location.sector.eq(getProfile(foundMember).getLocation().getSector())
                                .or(profile.location.sector.isNotNull())
                )
                .fetchFirst();
    }

    public ProfileResponse findRecentProfile(final InternalProfileFilterRequest request,
                                             final Long memberId) {
        Gender foundGender = findMemberGender(memberId);
        JPAQuery<ProfileResponse> profileFindQuery = buildProfileFindQuery(request, foundGender);

        return profileFindQuery.where(member.id.ne(memberId))
                .orderBy(member.createdAt.desc())
                .fetchFirst();
    }

    public ProfileResponse findProfileByReligion(final InternalProfileFilterRequest request,
                                                 final Long memberId) {
        Member foundMember = findMember(memberId);
        JPAQuery<ProfileResponse> profileFindQuery = buildProfileFindQuery(request,
                getPhysicalProfile(foundMember).getGender());

        return profileFindQuery.where(profile.religion.eq(getProfile(foundMember).getReligion()))
                .fetchFirst();
    }

    public ProfileResponse findProfileByHobbies(final InternalProfileFilterRequest request,
                                                final Long memberId) {
        Member foundMember = findMember(memberId);
        Long memberHobbiesId = getMemberHobbiesId(foundMember);
        List<String> foundMemberHobbyCodes = findMemberHobbyCodesByMemberHobbiesId(memberHobbiesId);
        List<Long> foundMemberHobbiesIds = findMemberHobbiesIdByHobbyCodes(foundMemberHobbyCodes);

        return buildProfileFindByHobbiesQuery(
                request,
                getPhysicalProfile(foundMember).getGender(),
                foundMemberHobbiesIds
        ).fetchFirst();
    }

    private List<String> findMemberHobbyCodesByMemberHobbiesId(final Long memberHobbiesId) {
        if (memberHobbiesId == null) {
            return Collections.emptyList();
        }

        return jpaQueryFactory.select(hobby.code)
                .from(memberHobbies)
                .leftJoin(memberHobbies.hobbies, memberHobby)
                .leftJoin(memberHobby.hobby, hobby)
                .where(memberHobbies.id.eq(memberHobbiesId))
                .fetch();
    }

    private List<Long> findMemberHobbiesIdByHobbyCodes(final List<String> hobbyCodes) {
        if (hobbyCodes == null || hobbyCodes.isEmpty()) {
            return Collections.emptyList();
        }

        return jpaQueryFactory.select(memberHobbies.id)
                .from(memberHobbies)
                .leftJoin(memberHobbies.hobbies, memberHobby)
                .leftJoin(memberHobby.hobby, hobby)
                .where(hobby.code.in(hobbyCodes))
                .fetch();
    }

    private JPAQuery<ProfileResponse> buildProfileFindByHobbiesQuery(final InternalProfileFilterRequest request,
                                                                     final Gender gender,
                                                                     final List<Long> memberHobbiesIds) {
        if (request.hobbyCode() != null) {
            memberHobbiesIds.addAll(findMemberHobbiesIdByHobbyCode(request.hobbyCode()));
        }
        HashSet<Long> uniqueMemberHobbiesIds = new HashSet<>(memberHobbiesIds);

        return jpaQueryFactory.select(getProfileResponse())
                .from(member)
                .leftJoin(member.memberProfile, memberProfile)
                .leftJoin(memberProfile.profile, profile)
                .leftJoin(profile.physicalProfile, physicalProfile)
                .where(physicalProfile.gender.ne(gender),
                        profile.profileAccessStatus.eq(ProfileAccessStatus.PUBLIC),
                        memberProfile.profileAccessStatus.eq(ProfileAccessStatus.PUBLIC),
                        applyFilterCondition(request),
                        profile.memberHobbies.id.in(uniqueMemberHobbiesIds));
    }

}
