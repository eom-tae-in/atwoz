package com.atwoz.mission.application.membermission;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionPagingResponse;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberMissionsQueryService extends BaseQueryService<MemberMissionSimpleResponse> {

    private final MemberMissionsRepository memberMissionsRepository;

    public MemberMissionPagingResponse findMemberMissionsWithPaging(final Long memberId, final Pageable pageable) {
        Page<MemberMissionSimpleResponse> response = memberMissionsRepository.findMemberMissionsWithPaging(memberId, pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), response);
        return MemberMissionPagingResponse.of(response, nextPage);
    }
}
