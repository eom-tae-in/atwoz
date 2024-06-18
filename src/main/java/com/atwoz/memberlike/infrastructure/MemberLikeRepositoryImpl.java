package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberLikeRepositoryImpl implements MemberLikeRepository {

    private final MemberLikeJdbcRepository memberLikeJdbcRepository;
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
    public boolean isAlreadyExisted(final Long senderId, final Long receiverId) {
        return memberLikeJpaRepository.existsBySenderIdAndReceiverId(senderId, receiverId);
    }
}
