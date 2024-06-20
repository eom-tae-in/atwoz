package com.atwoz.memberlike.fixture;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.vo.LikeIcon;
import com.atwoz.memberlike.domain.vo.LikeLevel;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class MemberLikeFixture {

    private static final Long SENDER_ID = 1L;
    private static final Long RECEIVER_ID = 2L;

    public static MemberLike 만료된_호감_생성() {
        return MemberLike.builder()
                .senderId(SENDER_ID)
                .receiverId(RECEIVER_ID)
                .isRecent(false)
                .likeIcon(LikeIcon.NONE)
                .likeLevel(LikeLevel.DEFAULT_LIKE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static MemberLike 옛날_호감_생성() {
        return MemberLike.builder()
                .senderId(SENDER_ID)
                .receiverId(RECEIVER_ID)
                .isRecent(true)
                .likeIcon(LikeIcon.NONE)
                .likeLevel(LikeLevel.DEFAULT_LIKE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
