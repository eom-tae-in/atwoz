package com.atwoz.mission.intrastructure.membermission;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.profile.physical.Gender;
import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.intrastructure.membermission.dto.MemberMissionSimpleResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개_id없음;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private MemberMissionsQueryRepository memberMissionQueryRepository;

    @Autowired
    private MemberMissionsRepository memberMissionsRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Test
    void 회원_미션_목록_페이징_조회() {
        // given
        Long memberId = 1L;
        Gender memberGender = Gender.FEMALE;
        List<MemberMission> memberMissionList = new ArrayList<>();
        MemberMissions memberMissions = MemberMissions.createWithMemberId(memberId);

        for (int i = 0; i < 3; i++) {
            Mission mission = 미션_생성_리워드_100_데일리_공개_id없음();
            missionRepository.save(mission);
            MemberMission memberMission = MemberMission.createDefault(mission);
            memberMissions.addClearedMission(memberGender, memberMission);
            memberMissionList.add(memberMission);
        }
        memberMissionsRepository.save(memberMissions);

        PageRequest pageRequest = PageRequest.of(0, 3);

        // when
        Page<MemberMissionSimpleResponse> found = memberMissionQueryRepository.findMemberMissionsWithPaging(memberId, pageRequest);

        // then
        List<MemberMissionSimpleResponse> expected = memberMissionList.stream()
                .sorted(Comparator.comparing(MemberMission::getId).reversed())
                .limit(3)
                .map(memberMission -> new MemberMissionSimpleResponse(
                        memberMission.getMission().getId(),
                        memberMission.isDoesGetReward(),
                        memberMission.getMission().getReward(),
                        memberMission.getMission().getMissionType(),
                        memberMission.getCreatedAt()
                ))
                .toList();

        assertSoftly(softly -> {
            softly.assertThat(found).hasSize(3);
            softly.assertThat(found.hasNext()).isFalse();
            softly.assertThat(found.getContent())
                    .usingRecursiveComparison()
                    .ignoringFieldsOfTypes(LocalDateTime.class)
                    .isEqualTo(expected);
        });
    }
}
