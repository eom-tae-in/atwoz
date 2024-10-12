package com.atwoz.member.fixture.member;

import com.atwoz.member.application.member.dto.MemberContactInfoDuplicationCheckResponse;
import com.atwoz.member.domain.member.MemberPushNotifications;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.domain.member.vo.ContactType;
import com.atwoz.member.domain.member.vo.MemberAccountStatus;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;

@SuppressWarnings("NonAsciiCharacters")
public class 회원_응답_픽스처 {

    public static class 회원_푸시_알림_조회_응답_픽스처 {

        private static final boolean DEFAULT_IS_LIKE_RECEIVED_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_NEW_MESSAGE_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_PROFILE_EXCHANGE_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_PROFILE_IMAGE_CHANGE_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_LONG_TIME_LO_LOGIN_NOTIFICATION_ON = true;
        private static final boolean DEFAULT_IS_INTERVIEW_WRITING_REQUEST_NOTIFICATION_ON = true;

        public static MemberNotificationsResponse 회원_푸시_알림_조회_응답_생성() {
            return new MemberNotificationsResponse(
                    DEFAULT_IS_LIKE_RECEIVED_NOTIFICATION_ON,
                    DEFAULT_IS_NEW_MESSAGE_NOTIFICATION_ON,
                    DEFAULT_IS_PROFILE_EXCHANGE_NOTIFICATION_ON,
                    DEFAULT_IS_PROFILE_IMAGE_CHANGE_NOTIFICATION_ON,
                    DEFAULT_IS_LONG_TIME_LO_LOGIN_NOTIFICATION_ON,
                    DEFAULT_IS_INTERVIEW_WRITING_REQUEST_NOTIFICATION_ON
            );
        }

        public static MemberNotificationsResponse 회원_푸시_알림_조회_응답_생성_회원푸시알림목록(
                final MemberPushNotifications memberPushNotifications
        ) {
            return new MemberNotificationsResponse(
                    memberPushNotifications.isLikeReceivedNotificationOn(),
                    memberPushNotifications.isNewMessageNotificationOn(),
                    memberPushNotifications.isProfileExchangeNotificationOn(),
                    memberPushNotifications.isProfileImageChangeNotificationOn(),
                    memberPushNotifications.isLongTimeLoLoginNotificationOn(),
                    memberPushNotifications.isInterviewWritingRequestNotificationOn()
            );
        }
    }

    public static class 회원_계정_상태_조회_응답_픽스처 {

        private static final String DEFAULT_ACCOUNT_STATUS = MemberAccountStatus.ACTIVE.getStatus();

        public static MemberAccountStatusResponse 회원_계정_상태_조회_응답_생성() {
            return new MemberAccountStatusResponse(DEFAULT_ACCOUNT_STATUS);
        }

        public static MemberAccountStatusResponse 회원_계정_상태_조회_응답_생성_회원계정상태(
                final MemberAccountStatus memberAccountStatus) {

            return new MemberAccountStatusResponse(memberAccountStatus);
        }
    }

    public static class 회원_연락처_정보_조회_응답_픽스처 {

        private static final String DEFAULT_CONTACT_TYPE = ContactType.PHONE_NUMBER.getType();
        private static final String DEFAULT_CONTACT_VALUE = "01012345678";

        public static MemberContactInfoResponse 회원_연락처_정보_조회_응답_생성() {
            return new MemberContactInfoResponse(DEFAULT_CONTACT_TYPE, DEFAULT_CONTACT_VALUE);
        }

        public static MemberContactInfoResponse 회원_연락처_정보_조회_응답_생성_연락처(final Contact contact) {
            return new MemberContactInfoResponse(contact.getContactType().getType(), contact.getContactValue());
        }
    }

    public static class 회원_연락처_정보_중복_검증_응답_픽스처 {

        private static final boolean DEFAULT_IS_DUPLICATED = false;

        public static MemberContactInfoDuplicationCheckResponse 회원_연락처_정보_중복_검증_응답_생성() {
            return new MemberContactInfoDuplicationCheckResponse(DEFAULT_IS_DUPLICATED);
        }
    }
}
