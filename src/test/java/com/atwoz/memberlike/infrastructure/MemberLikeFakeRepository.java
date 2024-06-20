package com.atwoz.memberlike.infrastructure;

import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberLikeFakeRepository implements MemberLikeRepository {

    private static final int DELETION_THRESHOLD_DATE = 30;
    private static final int UPDATE_THRESHOLD_DATE = 2;

    private final Map<Long, MemberLike> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public MemberLike save(final MemberLike memberLike) {
        MemberLike savedMemberLike = MemberLike.builder()
                .id(id)
                .senderId(memberLike.getSenderId())
                .receiverId(memberLike.getReceiverId())
                .createdAt(memberLike.getCreatedAt())
                .updatedAt(memberLike.getUpdatedAt())
                .likeLevel(memberLike.getLikeLevel())
                .likeIcon(memberLike.getLikeIcon())
                .isRecent(memberLike.getIsRecent())
                .build();
        map.put(id, savedMemberLike);

        return map.get(id++);
    }

    @Override
    public void deleteExpiredLikes() {
        map.entrySet()
                .removeIf(entry -> isExpiredLike(entry.getValue()));
    }

    private boolean isExpiredLike(final MemberLike memberLike) {
        LocalDateTime passedTime = LocalDateTime.now()
                .minusDays(DELETION_THRESHOLD_DATE);

        return memberLike.getCreatedAt()
                .isBefore(passedTime);
    }

    @Override
    public void endRecentByTimeExpired() {
        map.values()
                .stream()
                .filter(this::isExpiredTimeLike)
                .forEach(memberLike -> {
                    MemberLike updatedMemberLike = changeRecentMemberLike(memberLike);
                    map.put(updatedMemberLike.getId(), updatedMemberLike);
                });
    }

    private boolean isExpiredTimeLike(final MemberLike memberLike) {
        LocalDateTime passedTime = LocalDateTime.now()
                .minusDays(UPDATE_THRESHOLD_DATE);

        return memberLike.getCreatedAt()
                .isBefore(passedTime);
    }

    private MemberLike changeRecentMemberLike(final MemberLike memberLike) {
        return MemberLike.builder()
                .id(memberLike.getId())
                .senderId(memberLike.getSenderId())
                .receiverId(memberLike.getReceiverId())
                .createdAt(memberLike.getCreatedAt())
                .updatedAt(memberLike.getUpdatedAt())
                .likeLevel(memberLike.getLikeLevel())
                .likeIcon(memberLike.getLikeIcon())
                .isRecent(!memberLike.getIsRecent())
                .build();
    }

    @Override
    public boolean isAlreadyExisted(final Long senderId, final Long receiverId) {
        return map.values()
                .stream()
                .anyMatch(memberLike -> isSame(memberLike, senderId, receiverId));
    }

    private boolean isSame(final MemberLike memberLike, final Long senderId, final Long receiverId) {
        return senderId.equals(memberLike.getSenderId()) && receiverId.equals(memberLike.getReceiverId());
    }

    @Override
    public Page<MemberLikeSimpleResponse> findSendLikesWithPaging(final Long senderId, final Pageable pageable) {
        List<MemberLike> memberLikes = map.values()
                .stream()
                .filter(memberLike -> isSame(memberLike, senderId, memberLike.getReceiverId()))
                .toList();

        int total = memberLikes.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);

        List<MemberLikeSimpleResponse> responses = memberLikes.subList(start, end)
                .stream()
                .map(memberLike -> convertToResponse(memberLike.getReceiverId(), memberLike))
                .limit(total)
                .toList();

        return new PageImpl<>(responses, pageable, total);
    }

    private MemberLikeSimpleResponse convertToResponse(Long id, MemberLike memberLike) {
        return new MemberLikeSimpleResponse(
                id,
                "회원 " + id,
                "서울시",
                "강남구",
                20,
                memberLike.getLikeIcon().getName(),
                memberLike.getIsRecent()
        );
    }

    @Override
    public Page<MemberLikeSimpleResponse> findReceivedLikesWithPaging(final Long receiverId, final Pageable pageable) {
        List<MemberLike> memberLikes = map.values()
                .stream()
                .filter(memberLike -> isSame(memberLike, memberLike.getSenderId(), receiverId))
                .toList();

        int total = memberLikes.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), total);

        List<MemberLikeSimpleResponse> responses = memberLikes.subList(start, end)
                .stream()
                .map(memberLike -> convertToResponse(memberLike.getSenderId(), memberLike))
                .toList();

        return new PageImpl<>(responses, pageable, total);
    }
}
