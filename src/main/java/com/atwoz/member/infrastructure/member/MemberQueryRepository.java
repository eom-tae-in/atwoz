package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.infrastructure.member.dto.HobbiesResponse;
import com.atwoz.member.infrastructure.member.dto.MemberProfileResponse;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.StylesResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.atwoz.member.domain.member.QMemberProfile.memberProfile;
import static com.atwoz.member.domain.member.profile.QMemberHobby.memberHobby;
import static com.atwoz.member.domain.member.profile.QMemberStyle.memberStyle;
import static com.atwoz.member.domain.member.profile.QProfile.profile;
import static com.atwoz.member.domain.member.profile.physical.QPhysicalProfile.physicalProfile;
import static com.atwoz.member.domain.member.profile.vo.QMemberHobbies.memberHobbies;
import static com.atwoz.member.domain.member.profile.vo.QMemberStyles.memberStyles;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberResponse findMemberWithProfile(final Long memberId) {
        Member member = findMember(memberId);
        MemberProfileResponse memberProfileResponse = getMemberProfileResponse(member);
        HobbiesResponse hobbiesResponse = getHobbiesResponse(getMemberHobbiesId(member));
        StylesResponse styleResponse = getStylesResponse(getMemberStylesId(member));

        return new MemberResponse(memberProfileResponse, hobbiesResponse, styleResponse);
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

    private MemberProfileResponse getMemberProfileResponse(final Member member) {
        return MemberProfileResponse.createWith(
                member.getNickname(),
                member.getPhoneNumber(),
                getProfile(member).getJob(),
                getProfile(member).getLocation(),
                getProfile(member).getGraduate(),
                getProfile(member).getSmoke(),
                getProfile(member).getDrink(),
                getProfile(member).getReligion(),
                getProfile(member).getMbti(),
                getPhysicalProfile(member).getAge(),
                getPhysicalProfile(member).getHeight(),
                getPhysicalProfile(member).getGender()
        );
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

    private HobbiesResponse getHobbiesResponse(final Long memberHobbiesId) {
        return HobbiesResponse.createWith(jpaQueryFactory.select(memberHobby.hobby)
                .from(memberHobbies)
                .innerJoin(memberHobbies.hobbies, memberHobby)
                .where(memberHobbies.id.eq(memberHobbiesId))
                .fetch());
    }

    private StylesResponse getStylesResponse(final Long memberStylesId) {
        return StylesResponse.createWith(jpaQueryFactory.select(memberStyle.style)
                .from(memberStyles)
                .innerJoin(memberStyles.styles, memberStyle)
                .where(memberStyles.id.eq(memberStylesId))
                .fetch());
    }
}
