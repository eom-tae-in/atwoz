package com.atwoz.mission.intrastructure.membermission;

import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MemberMissionsFakeRepository implements MemberMissionsRepository {

    private final Map<Long, MemberMissions> map = new HashMap<>();

    private Long id = 1L;

    @Override
    public MemberMissions save(final MemberMissions memberMissions) {
        MemberMissions newMemberMissions = MemberMissions.builder()
                .id(id)
                .memberId(memberMissions.getMemberId())
                .memberMissions(memberMissions.getMemberMissions())
                .build();
        map.put(id, newMemberMissions);
        id++;
        return newMemberMissions;
    }

    @Override
    public Optional<MemberMissions> findByMemberId(final Long memberId) {
        return map.values().stream()
                .filter(memberMissions -> memberId.equals(memberMissions.getMemberId()))
                .findAny();
    }

    @Override
    public Page<MemberMissionSimpleResponse> findMemberMissionsWithPaging(final Long memberId, final Pageable pageable) {
        MemberMissions memberMissions = map.values().stream()
                .filter(m -> memberId.equals(m.getMemberId()))
                .findAny()
                .orElse(MemberMissions.builder().build());

        List<MemberMissionSimpleResponse> responses = collectMemberMissionSimpleResponses(memberMissions);
        return new PageImpl<>(responses, pageable, responses.size());
    }

    private List<MemberMissionSimpleResponse> collectMemberMissionSimpleResponses(final MemberMissions memberMissions) {
        return memberMissions.getMemberMissions().stream()
                .map(this::convertMemberMissionSimpleResponse)
                .toList();
    }

    private MemberMissionSimpleResponse convertMemberMissionSimpleResponse(final MemberMission memberMission) {
        return new MemberMissionSimpleResponse(
                memberMission.getMission().getId(),
                memberMission.isDoesGetReward(),
                memberMission.getMission().getReward(),
                memberMission.getMission().getMissionType(),
                memberMission.getCreatedAt()
        );
    }
}
