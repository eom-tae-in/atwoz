package com.atwoz.alert.domain;

import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.domain.vo.AlertMessage;
import com.atwoz.global.domain.SoftDeleteBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Alert extends SoftDeleteBaseEntity {

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
    private Long receiverId;

    private Alert(final AlertGroup alertGroup, final AlertMessage alertMessage, final Long receiverId) {
        this.alertGroup = alertGroup;
        this.alertMessage = alertMessage;
        this.receiverId = receiverId;
        this.isRead = false;
    }

    public static Alert createWith(final AlertGroup group, final String title, final String body, final Long receiverId) {
        AlertMessage message = AlertMessage.createWith(title, body);
        return new Alert(group, message, receiverId);
    }

    public void read() {
        if (!isRead) {
            this.isRead = true;
        }
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

    public boolean isRead() {
        return isRead;
    }
}
