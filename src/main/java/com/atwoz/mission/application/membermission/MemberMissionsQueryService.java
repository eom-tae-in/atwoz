package com.atwoz.mission.application.membermission;

import com.atwoz.mission.domain.membermission.MemberMissionRepository;
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
public class MemberMissionsQueryService {

    private final MemberMissionRepository memberMissionRepository;

    public MemberMissionPagingResponse findMemberMissionsWithPaging(final Long memberId, final Pageable pageable) {
        Page<MemberMissionSimpleResponse> response = memberMissionRepository.findMemberMissionsWithPaging(memberId, pageable);
        return MemberMissionPagingResponse.of(response, pageable);
    }
}
