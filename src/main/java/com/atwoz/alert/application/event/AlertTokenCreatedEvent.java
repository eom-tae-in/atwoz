package com.atwoz.alert.application.event;

public record AlertTokenCreatedEvent(
        Long id,
        String token
) {
}
