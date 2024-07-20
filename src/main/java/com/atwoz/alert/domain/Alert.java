package com.atwoz.alert.domain;

import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.domain.vo.AlertMessage;
import com.atwoz.global.domain.SoftDeleteBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "alert_index", columnList = "receiver_id, deleted_at, created_at DESC, id DESC")})
@Entity
public class Alert extends SoftDeleteBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AlertGroup alertGroup;

    @Embedded
    @Column(nullable = false)
    private AlertMessage alertMessage;

    @Column(nullable = false)
    private Long receiverId;

    public static Alert createWith(final AlertGroup group, final String title, final String body, final Long receiverId) {
        AlertMessage message = AlertMessage.createWith(title, body);
        return Alert.builder()
                .alertGroup(group)
                .alertMessage(message)
                .receiverId(receiverId)
                .isRead(false)
                .build();
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
