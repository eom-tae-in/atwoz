package com.atwoz.mission.domain.membermission;

import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberMissionsRepository {

    MemberMissions save(MemberMissions memberMissions);

    Optional<MemberMissions> findByMemberId(Long memberId);

    Page<MemberMissionSimpleResponse> findMemberMissionsWithPaging(Long memberId, Pageable pageable);
}
