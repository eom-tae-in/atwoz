package com.atwoz.alert.domain;

import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.domain.vo.AlertMessage;
import com.atwoz.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Alert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    @Enumerated
    private AlertGroup alertGroup;

    @Embedded
    @Column(nullable = false)
    private AlertMessage alertMessage;

    @Column(nullable = false)
    private String token;

    private Alert(final AlertGroup alertGroup, final AlertMessage alertMessage, final String token) {
        this.alertGroup = alertGroup;
        this.alertMessage = alertMessage;
        this.token = token;
    }

    public static Alert createWith(final String group, final String title, final String body, final String token) {
        AlertGroup alertGroup = AlertGroup.findByName(group);
        AlertMessage message = AlertMessage.createWith(title, body);
        return new Alert(alertGroup, message, token);
    }

    public void read() {
        this.isRead = true;
    }

    public String getTitle() {
        return alertMessage.getTitle();
    }

    public String getBody() {
        return alertMessage.getBody();
    }

    public String getGroup() {
        return alertGroup.getName();
    }
}
