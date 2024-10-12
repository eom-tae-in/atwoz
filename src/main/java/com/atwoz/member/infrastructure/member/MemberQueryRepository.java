package com.atwoz.member.infrastructure.member;

import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.QMember.member;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberNotificationsResponse findMemberNotifications(final Long memberId) {
        return jpaQueryFactory.select(constructor(MemberNotificationsResponse.class,
                        member.memberPushNotifications.isLikeReceivedNotificationOn,
                        member.memberPushNotifications.isNewMessageNotificationOn,
                        member.memberPushNotifications.isProfileExchangeNotificationOn,
                        member.memberPushNotifications.isProfileImageChangeNotificationOn,
                        member.memberPushNotifications.isLongTimeLoLoginNotificationOn,
                        member.memberPushNotifications.isInterviewWritingRequestNotificationOn
                )).from(member)
                .where(member.id.eq(memberId))
                .fetchFirst();
    }

    public MemberAccountStatusResponse findMemberAccountStatus(final Long memberId) {
        return jpaQueryFactory.select(constructor(MemberAccountStatusResponse.class,
                        member.memberAccountStatus
                )).from(member)
                .where(member.id.eq(memberId))
                .fetchFirst();
    }

    public MemberContactInfoResponse findMemberContactInfo(final Long memberId) {
        return jpaQueryFactory.select(constructor(MemberContactInfoResponse.class,
                        member.contact.contactType,
                        member.contact.contactValue
                )).from(member)
                .where(member.id.eq(memberId))
                .fetchFirst();
    }
}
