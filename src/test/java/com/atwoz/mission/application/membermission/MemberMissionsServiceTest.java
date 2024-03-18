package com.atwoz.mission.application.membermission;

import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotClearException;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotFoundException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionsNotFoundException;
import com.atwoz.mission.intrastructure.membermission.MemberMissionsFakeRepository;
import com.atwoz.mission.intrastructure.mission.MissionFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_진행중;
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

        Mission mission = 미션_생성_리워드_100_데일리_공개();
        Mission saveMission = missionRepository.save(mission);

        // when
        memberMissionsService.addMemberMission(memberMissions.getMemberId(), saveMission.getId());

        // then
        assertThat(memberMissions.getMemberMissions()).isNotEmpty();
    }

    @Test
    void 회원의_미션_목록에서_특정_미션을_클리어한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);

        Mission mission = memberMission.getMission();

        // when
        memberMissionsService.clearMemberMission(memberMissions.getMemberId(), mission.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberMission.isDoesGetReward()).isFalse();
            softly.assertThat(memberMission.isStatusClear()).isTrue();
        });
    }

    @Test
    void 회원의_미션_목록에서_특정_미션의_보상을_구한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);

        Mission mission = memberMission.getMission();

        // when
        Integer reward = memberMissionsService.getRewardByMissionId(memberMissions.getMemberId(), mission.getId());

        // then
        assertThat(reward).isEqualTo(mission.getReward());
    }

    @Test
    void 회원의_미션_목록에서_완료된_미션의_보상_총합을_구한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);
        int expectedReward = 미션_생성_리워드_100_데일리_공개().getReward();

        // when
        Integer reward = memberMissionsService.receiveAllClearMissionsRewards(memberMissions.getMemberId());

        // then
        assertThat(reward).isEqualTo(expectedReward);
    }

    @Nested
    class 회원_미션_예외 {

        @Test
        void 회원_미션_목록애_잘못된_미션을_넣으면_예외가_발생한다() {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);

            Long missionId = -1L;

            // when & then
            assertThatThrownBy(() -> memberMissionsService.addMemberMission(memberMissions.getMemberId(), missionId))
                    .isInstanceOf(MissionNotFoundException.class);
        }

        @Test
        void 회원의_미션_목록에서_없는_미션을_클리어하면_예외가_발생한다() {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);

            Long missionId = -1L;

            // when & then
            assertThatThrownBy(() -> memberMissionsService.clearMemberMission(memberMissions.getMemberId(), missionId))
                    .isInstanceOf(MemberMissionNotFoundException.class);
        }

        @Test
        void 회원의_미션_목록이_없을_때_미션을_클리어하면_예외가_발생한다() {
            // given
            Long memberId = -1L;
            Mission mission = 미션_생성_리워드_100_데일리_공개();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.clearMemberMission(memberId, mission.getId()))
                    .isInstanceOf(MemberMissionsNotFoundException.class);
        }

        @Test
        void 회원의_미션_목록에서_없는_미션의_보상을_구하면_예외가_발생한다() {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);

            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
            Mission mission = memberMission.getMission();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.getRewardByMissionId(memberMissions.getMemberId(), mission.getId()))
                    .isInstanceOf(MissionNotFoundException.class);
        }

        @Test
        void 회원의_미션_목록에서_완료되지_않은_미션의_보상을_구하면_예외가_발생한다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_진행중();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
            memberMissionsRepository.save(memberMissions);

            Mission mission = memberMission.getMission();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.getRewardByMissionId(memberMissions.getMemberId(), mission.getId()))
                    .isInstanceOf(MemberMissionNotClearException.class);
        }

        @Test
        void 회원의_미션_목록이_없을_때_완료된_미션의_보상_총합을_구하면_예외가_발생한다() {
            // given
            Long memberId = -1L;

            // when & then
            assertThatThrownBy(() -> memberMissionsService.receiveAllClearMissionsRewards(memberId))
                    .isInstanceOf(MemberMissionsNotFoundException.class);
        }
    }
}
