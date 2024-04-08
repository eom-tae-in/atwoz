package com.atwoz.mission.application.membermission;

import com.atwoz.member.domain.info.profile.body.Gender;
import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionsNotFoundException;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberMissionsService {
    
    private final MemberMissionsRepository memberMissionsRepository;
    private final MissionRepository missionRepository;

    public void addClearedMemberMission(final Long memberId, final Gender memberGender, final Long missionId) {
        MemberMissions memberMissions = memberMissionsRepository.findByMemberId(memberId)
                .orElseGet(() -> createNewMemberMissionsWithMemberId(memberId));

        Mission mission = findMissionByMissionId(missionId);
        MemberMission memberMission = MemberMission.createDefault(mission);
        memberMissions.addClearedMission(memberGender, memberMission);
    }

    private MemberMissions createNewMemberMissionsWithMemberId(final Long memberId) {
        MemberMissions memberMissions = MemberMissions.createWithMemberId(memberId);
        memberMissionsRepository.save(memberMissions);
        return memberMissions;
    }

    private Mission findMissionByMissionId(final Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
    }

    public Integer receiveRewardByMissionId(final Long memberId, final Long missionId) {
        MemberMissions memberMissions = findMemberMissionsByMemberId(memberId);
        return memberMissions.receiveRewardBy(missionId);
    }

    private MemberMissions findMemberMissionsByMemberId(final Long memberId) {
        return memberMissionsRepository.findByMemberId(memberId)
                .orElseThrow(MemberMissionsNotFoundException::new);
    }
}
