package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.domain.MemberLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikeJpaRepository extends JpaRepository<MemberLike, Long> {

    MemberLike save(MemberLike memberLike);
    boolean isExistsBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
