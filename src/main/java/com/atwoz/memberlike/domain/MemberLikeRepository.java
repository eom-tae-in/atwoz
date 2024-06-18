package com.atwoz.memberlike.domain;

public interface MemberLikeRepository {

    MemberLike save(MemberLike memberLike);
    void deleteExpiredLikes();
    void endRecentByTimeExpired();
    boolean isAlreadyExisted(Long senderId, Long receiverId);
}
