package com.atwoz.member.fixture.member;

import com.atwoz.member.application.member.dto.MemberAccountStatusPatchRequest;
import com.atwoz.member.application.member.dto.MemberContactInfoPatchRequest;
import com.atwoz.member.application.member.dto.MemberNotificationsPatchRequest;
import com.atwoz.member.domain.member.MemberPushNotifications;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.domain.member.vo.ContactType;
import com.atwoz.member.domain.member.vo.MemberAccountStatus;

@SuppressWarnings("NonAsciiCharacters")
public class 회원_요청_픽스처 {

    public static class 회원_푸시_알림_변경_요청_픽스처 {

        private static final boolean DEFAULT_IS_LIKE_RECEIVED_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_NEW_MESSAGE_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_PROFILE_EXCHANGE_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_PROFILE_IMAGE_CHANGE_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_LONG_TIME_LO_LOGIN_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_INTERVIEW_WRITING_REQUEST_NOTIFICATION_ON = true;

        public static MemberNotificationsPatchRequest 회원_푸시_알림_변경_요청_생성() {
            return new MemberNotificationsPatchRequest(
                    DEFAULT_IS_LIKE_RECEIVED_NOTIFICATION_ON,
                    DEFAULT_IS_NEW_MESSAGE_NOTIFICATION_ON,
                    DEFAULT_IS_PROFILE_EXCHANGE_NOTIFICATION_ON,
                    DEFAULT_IS_PROFILE_IMAGE_CHANGE_NOTIFICATION_ON,
                    DEFAULT_IS_LONG_TIME_LO_LOGIN_NOTIFICATION_ON,
                    DEFAULT_IS_INTERVIEW_WRITING_REQUEST_NOTIFICATION_ON
            );
        }

        public static MemberNotificationsPatchRequest 회원_푸시_알림_변경_요청_생성_회원푸시알림목록(
                final MemberPushNotifications memberPushNotifications
        ) {
            return new MemberNotificationsPatchRequest(
                    memberPushNotifications.isLikeReceivedNotificationOn(),
                    memberPushNotifications.isNewMessageNotificationOn(),
                    memberPushNotifications.isProfileExchangeNotificationOn(),
                    memberPushNotifications.isProfileImageChangeNotificationOn(),
                    memberPushNotifications.isLongTimeLoLoginNotificationOn(),
                    memberPushNotifications.isInterviewWritingRequestNotificationOn()
            );
        }
    }

    public static class 회원_계정_상태_변경_요청_픽스처 {

        private static final String DEFAULT_ACCOUNT_STATUS = MemberAccountStatus.ACTIVE.getStatus();

        public static MemberAccountStatusPatchRequest 회원_계정_상태_변경_요청_생성() {
            return new MemberAccountStatusPatchRequest(DEFAULT_ACCOUNT_STATUS);
        }

        public static MemberAccountStatusPatchRequest 회원_계정_상태_변경_요청_생성_회원계정상태(
                final MemberAccountStatus memberAccountStatus
        ) {
            return new MemberAccountStatusPatchRequest(memberAccountStatus.getStatus());
        }
    }

    public static class 회원_연락처_정보_변경_요청_픽스처 {

        private static final String DEFAULT_CONTACT_TYPE = ContactType.PHONE_NUMBER.getType();
        private static final String DEFAULT_CONTACT_VALUE = "01011111111";

        public static MemberContactInfoPatchRequest 회원_연락처_정보_변경_요청_생성() {
            return new MemberContactInfoPatchRequest(DEFAULT_CONTACT_TYPE, DEFAULT_CONTACT_VALUE);
        }

        public static MemberContactInfoPatchRequest 회원_연락처_정보_변경_요청_생성_연락처(final Contact contact) {
            return new MemberContactInfoPatchRequest(contact.getContactType().getType(), contact.getContactValue());
        }
    }
}
