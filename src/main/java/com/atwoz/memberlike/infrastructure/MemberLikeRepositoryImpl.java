package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberLikeRepositoryImpl implements MemberLikeRepository {

    private final MemberLikeJdbcRepository memberLikeJdbcRepository;
    private final MemberLikeQueryRepository memberLikeQueryRepository;
    private final MemberLikeJpaRepository memberLikeJpaRepository;

    @Override
    public MemberLike save(final MemberLike memberLike) {
        return memberLikeJpaRepository.save(memberLike);
    }

    @Override
    public void deleteExpiredLikes() {
        memberLikeJdbcRepository.deleteExpiredLikes();
    }

    @Override
    public void endRecentByTimeExpired() {
        memberLikeJdbcRepository.endRecentByTimeExpired();
    }

    @Override
    public boolean isAlreadyExisted(final Long senderId, final Long receiverId) {
        return memberLikeJpaRepository.existsBySenderIdAndReceiverId(senderId, receiverId);
    }

    @Override
    public Page<MemberLikeSimpleResponse> findSendLikesWithPaging(final Long senderId, final Pageable pageable) {
        return memberLikeQueryRepository.findSendLikesWithPaging(senderId, pageable);
    }

    @Override
    public Page<MemberLikeSimpleResponse> findReceiveLikesWithPaging(final Long receiverId, final Pageable pageable) {
        return memberLikeQueryRepository.findReceivedLikesWithPaging(receiverId, pageable);
    }
}
