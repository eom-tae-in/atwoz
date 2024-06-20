package com.atwoz.memberlike.application;

import com.atwoz.memberlike.domain.MemberLikeRepository;
import com.atwoz.memberlike.infrastructure.dto.MemberLikePagingResponse;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberLikeQueryService {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    private final MemberLikeRepository memberLikeRepository;

    public MemberLikePagingResponse findSendLikesWithPaging(final Long memberId, final Pageable pageable) {
        Page<MemberLikeSimpleResponse> response = memberLikeRepository.findSendLikesWithPaging(memberId, pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), response);
        return MemberLikePagingResponse.of(response, nextPage);
    }

    private int getNextPage(final int pageNumber, final Page<MemberLikeSimpleResponse> memberLikes) {
        if (memberLikes.hasNext()) {
            return pageNumber + NEXT_PAGE_INDEX;
        }

        return NO_MORE_PAGE;
    }

    public MemberLikePagingResponse findReceivedLikesWithPaging(final Long memberId, final Pageable pageable) {
        Page<MemberLikeSimpleResponse> response = memberLikeRepository.findReceivedLikesWithPaging(memberId, pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), response);
        return MemberLikePagingResponse.of(response, nextPage);
    }
}
