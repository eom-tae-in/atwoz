package com.atwoz.mission.application.membermission;

import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberMissionsService {
    
    private final MemberMissionsRepository memberMissionsRepository;
    private final MissionRepository missionRepository;

    public Integer getAllClearMissionsRewards(final Long memberId) {
        MemberMissions memberMissions = findMemberMissionsByMemberId(memberId);
        return memberMissions.getTotalClearedReward();
    }

    private MemberMissions findMemberMissionsByMemberId(final Long memberId) {
        return memberMissionsRepository.findByMemberId(memberId)
                .orElseThrow(MemberMissionsNotFoundException::new);
    }

    public void addMemberMission(final Long memberId, final Long missionId) {
        MemberMissions memberMissions = memberMissionsRepository.findByMemberId(memberId)
                .orElseGet(() -> createNewMemberMissionsWithMemberId(memberId));

        Mission mission = findMissionByMissionId(missionId);

        memberMissions.addMission(MemberMission.createDefault(mission));
    }

    private Mission findMissionByMissionId(final Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
    }

    private MemberMissions createNewMemberMissionsWithMemberId(final Long memberId) {
        MemberMissions memberMissions = MemberMissions.createWithMemberId(memberId);
        memberMissionsRepository.save(memberMissions);
        return memberMissions;
    }

    public Integer getRewardByMissionId(final Long memberId, final Long missionId) {
        MemberMissions memberMissions = findMemberMissionsByMemberId(memberId);
        return memberMissions.getRewardBy(missionId);
    }

    public void clearMemberMission(final Long memberId, final Long missionId) {
        MemberMissions memberMissions = findMemberMissionsByMemberId(memberId);
        memberMissions.clearMission(missionId);
    }
}
