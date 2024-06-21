package com.atwoz.memberlike.fixture;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.vo.LikeIcon;
import com.atwoz.memberlike.domain.vo.LikeLevel;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class MemberLikeFixture {

    private static final int EXPIRED_LIKE_DAY = 31;
    private static final int EXPIRED_TIME_LIKE_DAY = 3;

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

    public static MemberLike 호감_생성_id_없음() {
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

    public static MemberLike 보낸_지_31일_된_호감_생성() {
        return MemberLike.builder()
                .senderId(SENDER_ID)
                .receiverId(RECEIVER_ID)
                .isRecent(false)
                .likeIcon(LikeIcon.NONE)
                .likeLevel(LikeLevel.DEFAULT_LIKE)
                .createdAt(LocalDateTime.now()
                        .minusDays(EXPIRED_LIKE_DAY))
                .updatedAt(LocalDateTime.now()
                        .minusDays(EXPIRED_LIKE_DAY))
                .build();
    }

    public static MemberLike 보낸_지_사흘_된_호감_생성() {
        return MemberLike.builder()
                .senderId(SENDER_ID)
                .receiverId(RECEIVER_ID)
                .isRecent(true)
                .likeIcon(LikeIcon.NONE)
                .likeLevel(LikeLevel.DEFAULT_LIKE)
                .createdAt(LocalDateTime.now()
                        .minusDays(EXPIRED_TIME_LIKE_DAY))
                .updatedAt(LocalDateTime.now()
                        .minusDays(EXPIRED_TIME_LIKE_DAY))
                .build();
    }

    public static MemberLike 호감_생성_id_주입(final Long senderId, final Long receiverId) {
        return MemberLike.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .isRecent(true)
                .likeIcon(LikeIcon.NONE)
                .likeLevel(LikeLevel.DEFAULT_LIKE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static MemberLike 호감_생성_id_날짜_주입(final Long senderId, final Long receiverId, final int day) {
        return MemberLike.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .isRecent(true)
                .likeIcon(LikeIcon.NONE)
                .likeLevel(LikeLevel.DEFAULT_LIKE)
                .createdAt(LocalDateTime.now()
                        .minusDays(day))
                .updatedAt(LocalDateTime.now()
                        .minusDays(day))
                .build();
    }
}
