package com.atwoz.alert.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AlertMessage {

    private String title;
    private String body;

    public static AlertMessage createWith(final String title, final String body) {
        return new AlertMessage(title, body);
    }
}
