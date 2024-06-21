package com.atwoz.memberlike.domain;

import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberLikeRepository {

    MemberLike save(MemberLike memberLike);
    void deleteExpiredLikes();
    void endRecentByTimeExpired();
    boolean isAlreadyExisted(Long senderId, Long receiverId);
    Page<MemberLikeSimpleResponse> findSendLikesWithPaging(Long senderId, Pageable pageable);
    Page<MemberLikeSimpleResponse> findReceivedLikesWithPaging(Long receiverId, Pageable pageable);
}
