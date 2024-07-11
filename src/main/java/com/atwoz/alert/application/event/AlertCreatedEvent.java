package com.atwoz.alert.application.event;

import com.atwoz.alert.domain.vo.AlertGroup;

public record AlertCreatedEvent(
        AlertGroup group,
        String title,
        String body,
        String sender,
        Long receiverId,
        String token
) {
}
