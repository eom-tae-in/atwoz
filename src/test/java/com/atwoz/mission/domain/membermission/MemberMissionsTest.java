package com.atwoz.mission.domain.membermission;

import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_완료;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_진행중;
import static com.atwoz.mission.fixture.MemberMissionsFixture.멤버_미션들_생성;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsTest {

    @Nested
    class 개별적으로_미션_보상_받기 {

        @Test
        void 미션_id로_보상을_정상적으로_받는다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
            int expectedReward = 미션_생성_리워드_100_데일리_공개().getReward();

            // when
            Integer reward = memberMissions.receiveRewardBy(memberMission.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(reward).isEqualTo(expectedReward);
                softly.assertThat(memberMission.isDoesGetReward()).isTrue();
            });
        }

        @Test
        void 미션_id가_회원의_미션_목록에_존재하지_않으면_보상을_받을_수_없다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);

            // when & then
            assertThatThrownBy(() -> memberMissions.receiveRewardBy(-1L))
                    .isInstanceOf(MissionNotFoundException.class);
        }

        @Test
        void 이미_보상을_받은_미션은_다시_보상받을_수_없다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_완료();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
            Long missionId = 1L;

            // when & then
            assertThatThrownBy(() -> memberMissions.receiveRewardBy(missionId))
                    .isInstanceOf(MemberMissionAlreadyRewardedException.class);
        }
    }

    @Test
    void 완료한_미션의_보상받지_않은_보상을_모두_받는다() {
        // given
        MemberMission notRewardedMission = 멤버_미션_생성_완료_보상_수령_안함();
        MemberMission rewardedMission = 멤버_미션_생성_완료_보상_수령_완료();
        MemberMission notClearedMission = 멤버_미션_생성_진행중();
        MemberMissions memberMissions = 멤버_미션들_생성(notRewardedMission, notClearedMission, rewardedMission);
        int expectedReward = 미션_생성_리워드_100_데일리_공개().getReward();

        // when
        Integer result = memberMissions.receiveTotalClearedReward();

        // then
        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(expectedReward);
            softly.assertThat(notRewardedMission.isDoesGetReward()).isTrue();
        });
    }

    @Test
    void 미션을_추가한다() {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성(멤버_미션_생성_완료_보상_수령_안함(), 멤버_미션_생성_진행중());
        int beforeSize = memberMissions.getMemberMissions().size();

        // when
        memberMissions.addMission(멤버_미션_생성_완료_보상_수령_안함());

        // then
        assertThat(memberMissions.getMemberMissions().size()).isEqualTo(beforeSize + 1);
    }
}
