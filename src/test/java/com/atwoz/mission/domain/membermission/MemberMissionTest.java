package com.atwoz.mission.domain.membermission;

import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotClearException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_완료;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_진행중;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionTest {

    @Test
    void 미션을_클리어한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_진행중();
        boolean beforeStatus = memberMission.isStatusClear();

        // when
        memberMission.clearMission();

        // then
        assertThat(memberMission.isStatusClear()).isEqualTo(!beforeStatus);
    }

    @Nested
    class 보상을_받는다 {

        @Test
        void 보상을_정상적으로_받는다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함();

            // when
            Integer reward = memberMission.receiveReward();

            // then
            assertSoftly(softly -> {
                softly.assertThat(reward).isEqualTo(100);
                softly.assertThat(memberMission.isDoesGetReward()).isTrue();
            });
        }

        @Test
        void 미션을_클리어하지_않고_보상을_받으면_예외를_발생시킨다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_진행중();

            // when & then
            assertThatThrownBy(memberMission::receiveReward)
                    .isInstanceOf(MemberMissionNotClearException.class);
        }

        @Test
        void 이미_보상을_받은_미션은_다시_보상받을_수_없다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_완료();

            // when & then
            assertThatThrownBy(memberMission::receiveReward)
                    .isInstanceOf(MemberMissionAlreadyRewardedException.class);
        }
    }
}
