package com.atwoz.alert.fixture;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.domain.vo.AlertMessage;

import java.time.LocalDateTime;

public class AlertFixture {

    public static Alert 옛날_알림_생성() {
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
}
