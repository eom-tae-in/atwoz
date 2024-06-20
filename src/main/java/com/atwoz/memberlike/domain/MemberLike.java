package com.atwoz.memberlike.domain;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.memberlike.domain.vo.LikeIcon;
import com.atwoz.memberlike.domain.vo.LikeLevel;
import com.atwoz.memberlike.exception.exceptions.InvalidMemberLikeException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long receiverId;

    @Enumerated(value = EnumType.STRING)
    private LikeLevel likeLevel;

    @Enumerated(value = EnumType.STRING)
    private LikeIcon likeIcon;

    @Column(nullable = false)
    private Boolean isRecent;

    private MemberLike(final Long senderId, final Long receiverId, final LikeLevel likeLevel) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.likeLevel = likeLevel;
        this.likeIcon = LikeIcon.NONE;
        this.isRecent = true;
    }

    public static MemberLike createWith(final Long senderId, final Long receiverId, final String level) {
        validateNotSameUser(senderId, receiverId);

        return new MemberLike(senderId, receiverId, LikeLevel.findByName(level));
    }

    private static void validateNotSameUser(final Long senderId, final Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new InvalidMemberLikeException();
        }
    }

    public void updateLikeIcon(final LikeIcon likeIcon) {
        this.likeIcon = likeIcon;
    }
}
