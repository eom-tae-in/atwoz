package com.atwoz.memberlike.domain;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.memberlike.domain.vo.LikeIcon;
import com.atwoz.memberlike.domain.vo.LikeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
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
    private LikeType likeType;

    @Enumerated(value = EnumType.STRING)
    private LikeIcon likeIcon;

    @Column(nullable = false)
    private Boolean isRecent;

    private MemberLike(final Long senderId, final Long receiverId, final LikeType likeType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.likeType = likeType;
        this.likeIcon = LikeIcon.NONE;
        this.isRecent = true;
    }

    public static MemberLike createWith(final Long senderId, final Long receiverId, final String level) {
        return new MemberLike(senderId, receiverId, LikeType.findByName(level));
    }

    public void updateLikeIcon(final LikeIcon likeIcon) {
        this.likeIcon = likeIcon;
    }
}
