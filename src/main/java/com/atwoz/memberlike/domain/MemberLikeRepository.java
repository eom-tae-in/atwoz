package com.atwoz.memberlike.domain;

import com.atwoz.memberlike.infrastructure.dto.MemberLikePagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberLikeRepository {

    MemberLike save(MemberLike memberLike);
    void deleteExpiredLikes();
    void endRecentByTimeExpired();
    boolean isAlreadyExisted(Long senderId, Long receiverId);
    Page<MemberLikePagingResponse> findSendLikesWithPaging(Long senderId, Pageable pageable);
    Page<MemberLikePagingResponse> findReceiveLikesWithPaging(Long receiverId, Pageable pageable);
}
