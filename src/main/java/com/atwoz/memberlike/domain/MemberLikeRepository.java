package com.atwoz.memberlike.domain;

public interface MemberLikeRepository {

    MemberLike save(MemberLike memberLike);
    void deleteExpiredLikes();
    boolean isAlreadyExisted(Long senderId, Long receiverId);
}
