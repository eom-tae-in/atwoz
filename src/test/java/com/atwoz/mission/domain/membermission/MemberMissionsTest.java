package com.atwoz.mission.domain.membermission;

import com.atwoz.mission.exception.membermission.exceptions.AlreadyChallengeMissionExistedException;
import com.atwoz.mission.exception.membermission.exceptions.AlreadyDailyMissionExistedLimitException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotFoundException;
import com.atwoz.profile.domain.vo.Gender;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함_데일리;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함_데일리_id_시간있음;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_안함_챌린지;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_완료_데일리;
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
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리();
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
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);

            // when & then
            assertThatThrownBy(() -> memberMissions.receiveRewardBy(-1L))
                    .isInstanceOf(MemberMissionNotFoundException.class);
        }

        @Test
        void 이미_보상을_받은_미션은_다시_보상받을_수_없다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_완료_데일리();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
            Long missionId = 1L;

            // when & then
            assertThatThrownBy(() -> memberMissions.receiveRewardBy(missionId))
                    .isInstanceOf(MemberMissionAlreadyRewardedException.class);
        }
    }

    @ParameterizedTest(name = "성별이 [{0}]인 경우")
    @EnumSource(value = Gender.class)
    void 데일리_미션을_추가한다(final Gender gender) {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리();
        MemberMissions memberMissions = 멤버_미션들_생성();
        int beforeSize = memberMissions.getMemberMissions().size();

        // when
        memberMissions.addClearedMission(gender, memberMission);

        // then
        assertThat(memberMissions.getMemberMissions().size()).isEqualTo(beforeSize + 1);
    }

    @ParameterizedTest(name = "성별이 [{0}]일 때 같은 날 데일리 미션을 {1}개 등록할 경우")
    @MethodSource(value = "genderAndLimitData")
    void 회원은_성별에_따라_같은날_같은_데일리_미션을_진행할_수_있는_최대_횟수가_다르다(final Gender gender, final Long max) {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        for (long id = 1; id <= max; id++) {
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리_id_시간있음(id);
            memberMissions.addClearedMission(gender, memberMission);
        }

        // when & then
        assertThatThrownBy(() -> memberMissions.addClearedMission(gender, 멤버_미션_생성_완료_보상_수령_안함_데일리_id_시간있음(max + 1)))
                .isInstanceOf(AlreadyDailyMissionExistedLimitException.class);
    }

    static Stream<Arguments> genderAndLimitData() {
        return Stream.of(
                Arguments.of(Gender.MALE, 2L),
                Arguments.of(Gender.FEMALE, 3L)
        );
    }

    @ParameterizedTest(name = "성별이 [{0}]인 경우")
    @EnumSource(value = Gender.class)
    void 챌린지_미션을_추가한다(final Gender gender) {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_챌린지();
        MemberMissions memberMissions = 멤버_미션들_생성();
        int beforeSize = memberMissions.getMemberMissions().size();

        // when
        memberMissions.addClearedMission(gender, memberMission);

        // then
        assertThat(memberMissions.getMemberMissions().size()).isEqualTo(beforeSize + 1);
    }

    @ParameterizedTest(name = "성별이 [{0}]인 경우")
    @EnumSource(value = Gender.class)
    void 챌린지_미션은_한번만_가능하다(final Gender gender) {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_챌린지();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);

        // when & then
        assertThatThrownBy(() -> memberMissions.addClearedMission(gender, memberMission))
                .isInstanceOf(AlreadyChallengeMissionExistedException.class);
    }
}
