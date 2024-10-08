package com.atwoz.mission.application.membermission;

import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.membermission.exceptions.AlreadyChallengeMissionExistedException;
import com.atwoz.mission.exception.membermission.exceptions.AlreadyDailyMissionExistedLimitException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotFoundException;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import com.atwoz.mission.intrastructure.membermission.MemberMissionsFakeRepository;
import com.atwoz.mission.intrastructure.mission.MissionFakeRepository;
import com.atwoz.profile.domain.vo.Gender;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
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
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료_보상_수령_완료_데일리;
import static com.atwoz.mission.fixture.MemberMissionsFixture.멤버_미션들_생성;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개_id없음;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_200_챌린지_id없음;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_200_챌린지_공개;
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

    @ParameterizedTest(name = "성별이 [{0}]인 경우")
    @EnumSource(Gender.class)
    void 회원_미션_목록에_챌린지_미션을_등록한다(final Gender gender) {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        memberMissionsRepository.save(memberMissions);
        Mission mission = missionRepository.save(미션_생성_리워드_200_챌린지_id없음());

        // when
        memberMissionsService.addClearedMemberMission(memberMissions.getMemberId(), gender, mission.getId());

        // then
        assertThat(memberMissions.getMemberMissions()).isNotEmpty();
    }

    @ParameterizedTest(name = "성별이 [{0}]인 경우")
    @EnumSource(Gender.class)
    void 회원_미션_목록에_데일리_미션을_등록한다(final Gender gender) {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        memberMissionsRepository.save(memberMissions);
        Mission mission = missionRepository.save(미션_생성_리워드_100_데일리_공개_id없음());

        // when
        memberMissionsService.addClearedMemberMission(memberMissions.getMemberId(), gender, mission.getId());

        // then
        assertThat(memberMissions.getMemberMissions()).isNotEmpty();
    }

    @Test
    void 회원의_미션_목록에서_아직_보상을_수령하지_않은_특정_완료_미션의_보상을_수령한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리();
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

        @ParameterizedTest(name = "성별이 [{0}]인 경우")
        @EnumSource(Gender.class)
        void 회원_미션_목록에_같은_챌린지_미션이_또_들어가면_예외가_발생한다(final Gender gender) {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);
            memberMissions.addClearedMission(gender, MemberMission.createDefault(미션_생성_리워드_200_챌린지_공개()));
            Mission anotherMission = missionRepository.save(미션_생성_리워드_200_챌린지_id없음());

            // when & then
            assertThatThrownBy(() -> memberMissionsService.addClearedMemberMission(memberMissions.getMemberId(), gender, anotherMission.getId()))
                    .isInstanceOf(AlreadyChallengeMissionExistedException.class);
        }

        @ParameterizedTest(name = "성별이 [{0}]일 때 같은 날 데일리 미션을 {1}개 등록할 경우")
        @MethodSource("genderAndLimitData")
        void 회원은_성별에_따라_같은날_같은_데일리_미션을_진행할_수_있는_최대_횟수가_다르다(final Gender gender, final Long max) {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);
            Mission savedMission = missionRepository.save(미션_생성_리워드_100_데일리_공개());

            for (long id = 1; id <= max; id++) {
                memberMissions.addClearedMission(gender, 멤버_미션_생성_완료_보상_수령_안함_데일리_id_시간있음(id));
            }

            // when & then
            assertThatThrownBy(() -> memberMissionsService.addClearedMemberMission(memberMissions.getMemberId(), gender, savedMission.getId()))
                    .isInstanceOf(AlreadyDailyMissionExistedLimitException.class);
        }

        static Stream<Arguments> genderAndLimitData() {
            return Stream.of(
                    Arguments.of(Gender.MALE, 2L),
                    Arguments.of(Gender.FEMALE, 3L)
            );
        }

        @Test
        void 회원의_미션_목록에서_없는_미션의_보상을_수령하면_예외가_발생한다() {
            // given
            MemberMissions memberMissions = 멤버_미션들_생성();
            memberMissionsRepository.save(memberMissions);

            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_안함_데일리();
            Mission mission = memberMission.getMission();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.receiveRewardByMissionId(memberMissions.getMemberId(), mission.getId()))
                    .isInstanceOf(MemberMissionNotFoundException.class);
        }

        @Test
        void 이미_보상을_수령받은_미션을_다시_수령하면_예외가_발생한다() {
            // given
            MemberMission memberMission = 멤버_미션_생성_완료_보상_수령_완료_데일리();
            MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
            memberMissionsRepository.save(memberMissions);

            Mission mission = memberMission.getMission();

            // when & then
            assertThatThrownBy(() -> memberMissionsService.receiveRewardByMissionId(memberMissions.getMemberId(), mission.getId()))
                    .isInstanceOf(MemberMissionAlreadyRewardedException.class);
        }
    }
}
