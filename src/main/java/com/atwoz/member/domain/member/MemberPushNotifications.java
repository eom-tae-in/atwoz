package com.atwoz.member.domain.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberPushNotifications {

    private boolean isLikeReceivedNotificationOn;

    private boolean isNewMessageNotificationOn;

    private boolean isProfileExchangeNotificationOn;

    private boolean isProfileImageChangeNotificationOn;

    private boolean isLongTimeLoLoginNotificationOn;

    private boolean isInterviewWritingRequestNotificationOn;

    public static MemberPushNotifications createWith() {
        return MemberPushNotifications.builder()
                .isLikeReceivedNotificationOn(false)
                .isNewMessageNotificationOn(false)
                .isProfileExchangeNotificationOn(false)
                .isProfileImageChangeNotificationOn(false)
                .isLongTimeLoLoginNotificationOn(false)
                .isInterviewWritingRequestNotificationOn(false)
                .build();
    }

    public void update(
            final boolean isLikeReceivedNotificationOn,
            final boolean isNewMessageNotificationOn,
            final boolean isProfileExchangeNotificationOn,
            final boolean isProfileImageChangeNotificationOn,
            final boolean isLongTimeLoLoginNotificationOn,
            final boolean isInterviewWritingRequestNotificationOn
    ) {
        this.isLikeReceivedNotificationOn = isLikeReceivedNotificationOn;
        this.isNewMessageNotificationOn = isNewMessageNotificationOn;
        this.isProfileExchangeNotificationOn = isProfileExchangeNotificationOn;
        this.isProfileImageChangeNotificationOn = isProfileImageChangeNotificationOn;
        this.isLongTimeLoLoginNotificationOn = isLongTimeLoLoginNotificationOn;
        this.isInterviewWritingRequestNotificationOn = isInterviewWritingRequestNotificationOn;
    }
}
