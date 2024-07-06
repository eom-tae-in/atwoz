package com.atwoz.alert.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AlertMessage {

    private String title;
    private String body;

    private AlertMessage(final String title, final String body) {
        this.title = title;
        this.body = body;
    }

    public static AlertMessage createWith(final String title, final String body) {
        return new AlertMessage(title, body);
    }
}
