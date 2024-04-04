package com.atwoz.mission.application.membermission;

import com.atwoz.member.domain.info.profile.body.Gender;
import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotFoundException;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import com.atwoz.mission.intrastructure.membermission.MemberMissionsFakeRepository;
import com.atwoz.mission.intrastructure.mission.MissionFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_완료;
import static com.atwoz.mission.fixture.MemberMissionsFixture.멤버_미션들_생성;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsServiceTest {

    private MemberMissionsService memberMissionsService;
    private MemberMissionsRepository memberMissionsRepository;
    private MissionRepository missionRepository;

    @BeforeEach
    void init() {
        memberMissionsRepository = new MemberMissionsFakeRepository();
        missionRepository = new MissionFakeRepository();
        memberMissionsService = new MemberMissionsService(memberMissionsRepository, missionRepository);
    }

    @Test
    void 회원_미션_목록에_미션을_등록한다() {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        memberMissionsRepository.save(memberMissions);

        Gender memberGender = Gender.MALE;
        Mission mission = 미션_생성_리워드_100_데일리_공개();
        Mission saveMission = missionRepository.save(mission);

        // when
        memberMissionsService.addClearedMemberMission(memberMissions.getMemberId(), memberGender, saveMission.getId());

        // then
        assertThat(memberMissions.getMemberMissions()).isNotEmpty();
    }

    @Test
    void 회원의_미션_목록에서_아직_보상을_수령하지_않은_특정_완료_미션의_보상을_수령한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);

        Mission mission = memberMission.getMission();

        // when
        Integer reward = memberMissionsService.receiveRewardByMissionId(memberMissions.getMemberId(), mission.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(reward).isEqualTo(mission.getReward());
            softly.assertThat(memberMission.isDoesGetReward()).isTrue();
        });
    }

    @Nested
    class 회원_미션_예외 {

        @Test
        void 회원_미션_목록애_잘못된_미션을_넣으면_예외가_발생한다() {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);
            Gender memberGender = Gender.MALE;
            Long missionId = -1L;

            // when & then
            assertThatThrownBy(() -> memberMissionsService.addClearedMemberMission(memberMissions.getMemberId(), memberGender, missionId))
                    .isInstanceOf(MissionNotFoundException.class);
        }

        @Test
        void 회원의_미션_목록에서_없는_미션의_보상을_수령하면_예외가_발생한다() {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);

            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
            Mission mission = memberMission.getMission();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.receiveRewardByMissionId(memberMissions.getMemberId(), mission.getId()))
                    .isInstanceOf(MemberMissionNotFoundException.class);
        }

        @Test
        void 이미_보상을_수령받은_미션을_다시_수령하면_예외가_발생한다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_완료();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
            memberMissionsRepository.save(memberMissions);

            Mission mission = memberMission.getMission();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.receiveRewardByMissionId(memberMissions.getMemberId(), mission.getId()))
                    .isInstanceOf(MemberMissionAlreadyRewardedException.class);
        }
    }
}
