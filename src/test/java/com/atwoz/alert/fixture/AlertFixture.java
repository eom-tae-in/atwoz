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
}
