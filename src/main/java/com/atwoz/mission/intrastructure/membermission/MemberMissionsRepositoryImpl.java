package com.atwoz.mission.intrastructure.membermission;

import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberMissionsRepositoryImpl implements MemberMissionsRepository {

    private final MemberMissionsJpaRepository memberMissionsJpaRepository;
    private final MemberMissionsQueryRepository memberMissionsQueryRepository;

    @Override
    public MemberMissions save(final MemberMissions memberMissions) {
        memberMissionsJpaRepository.save(memberMissions);

        return memberMissions;
    }

    @Override
    public Optional<MemberMissions> findByMemberId(final Long memberId) {
        return memberMissionsJpaRepository.findByMemberId(memberId);
    }

    @Override
    public Page<MemberMissionSimpleResponse> findMemberMissionsWithPaging(final Long memberId, final Pageable pageable) {
        return memberMissionsQueryRepository.findMemberMissionsWithPaging(memberId, pageable);
    }
}
