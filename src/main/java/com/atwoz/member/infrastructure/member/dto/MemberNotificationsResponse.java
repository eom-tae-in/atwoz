package com.atwoz.member.infrastructure.member.dto;

public record MemberNotificationsResponse(

        boolean isLikeReceivedNotificationOn,
        boolean isNewMessageNotificationOn,
        boolean isProfileExchangeNotificationOn,
        boolean isProfileImageChangeNotificationOn,
        boolean isLongTimeLoLoginNotificationOn,
        boolean isInterviewWritingRequestNotificationOn
) {
}
