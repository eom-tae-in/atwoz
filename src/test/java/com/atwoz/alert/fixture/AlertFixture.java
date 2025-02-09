package com.atwoz.alert.fixture;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.domain.vo.AlertMessage;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class AlertFixture {

    private static final int DELETION_THRESHOLD = 61;

    public static Alert 옛날_알림_생성() {
        return Alert.builder()
                .isRead(false)
                .alertGroup(AlertGroup.ALERT)
                .alertMessage(AlertMessage.createWith("알림 제목", "알림 상세 내용"))
                .receiverId(1L)
                .createdAt(LocalDateTime.now()
                        .minusDays(DELETION_THRESHOLD))
                .updatedAt(LocalDateTime.now()
                        .minusDays(DELETION_THRESHOLD))
                .deletedAt(null)
                .build();
    }

    public static Alert 알림_생성_id_없음() {
        return Alert.builder()
                .isRead(false)
                .alertGroup(AlertGroup.ALERT)
                .alertMessage(AlertMessage.createWith("알림 제목", "알림 상세 내용"))
                .receiverId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(null)
                .build();
    }

    public static Alert 알림_생성_제목_날짜_주입(final String title, final int day) {
        return Alert.builder()
                .isRead(false)
                .alertGroup(AlertGroup.ALERT)
                .alertMessage(AlertMessage.createWith(title, "알림 상세 내용"))
                .receiverId(1L)
                .createdAt(LocalDateTime.now()
                        .plusDays(day))
                .updatedAt(LocalDateTime.now()
                        .plusDays(day))
                .deletedAt(null)
                .build();
    }

    public static Alert 알림_생성_제목_날짜_id_주입(final String title, final int day, final long id) {
        return Alert.builder()
                .id(id)
                .isRead(false)
                .alertGroup(AlertGroup.ALERT)
                .alertMessage(AlertMessage.createWith(title, "알림 상세 내용"))
                .receiverId(1L)
                .createdAt(LocalDateTime.now()
                        .plusDays(day))
                .updatedAt(LocalDateTime.now()
                        .plusDays(day))
                .deletedAt(null)
                .build();
    }

    public static Alert 알림_생성_제목_날짜_회원id_주입(final String title, final int day, final long receiverId) {
        return Alert.builder()
                .isRead(false)
                .alertGroup(AlertGroup.ALERT)
                .alertMessage(AlertMessage.createWith(title, "알림 상세 내용"))
                .receiverId(receiverId)
                .createdAt(LocalDateTime.now()
                        .plusDays(day))
                .updatedAt(LocalDateTime.now()
                        .plusDays(day))
                .deletedAt(null)
                .build();
    }

    public static Alert 알림_생성_id_있음() {
        return Alert.builder()
                .id(1L)
                .isRead(false)
                .alertGroup(AlertGroup.ALERT)
                .alertMessage(AlertMessage.createWith("알림 제목", "알림 상세 내용"))
                .receiverId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(null)
                .build();
    }
}
