package com.atwoz.memberlike.domain;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.memberlike.domain.vo.LikeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

    private MemberLike(final Long senderId, final Long receiverId, final LikeType likeType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.likeType = likeType;
    }

    public static MemberLike createWith(final Long senderId, final Long receiverId, final String level) {
        return new MemberLike(senderId, receiverId, LikeType.findByName(level));
    }
}
