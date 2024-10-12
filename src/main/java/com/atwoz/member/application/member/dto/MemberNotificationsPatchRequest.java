package com.atwoz.member.application.member.dto;

import jakarta.validation.constraints.NotNull;

public record MemberNotificationsPatchRequest(

        @NotNull(message = "내가받은 호감에 대한 푸시 알림 설정을 입력해주세요")
        Boolean isLikeReceivedNotificationOn,

        @NotNull(message = "새 메시지에 대한 푸시 알림 설정을 입력해주세요")
        Boolean isNewMessageNotificationOn,

        @NotNull(message = "프로필 교환 요청에 대한 푸시 알림 설정을 입력해주세요")
        Boolean isProfileExchangeNotificationOn,

        @NotNull(message = "프로필 사진 변경에 대한 푸시 알림 설정을 입력해주세요")
        Boolean isProfileImageChangeNotificationOn,

        @NotNull(message = "장기간 미접속 안내에 대한 푸시 알림 설정을 입력해주세요")
        Boolean isLongTimeLoLoginNotificationOn,

        @NotNull(message = "인터뷰 작성 권유에 대한 푸시 알림 설정을 입력해주세요")
        Boolean isInterviewWritingRequestNotificationOn
) {
}
