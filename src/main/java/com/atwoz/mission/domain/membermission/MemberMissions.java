package com.atwoz.mission.domain.membermission;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.member.domain.member.profile.physical_profile.Gender;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.exception.membermission.exceptions.AlreadyChallengeMissionExistedException;
import com.atwoz.mission.exception.membermission.exceptions.AlreadyDailyMissionExistedLimitException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.atwoz.member.domain.member.profile.physical_profile.Gender.FEMALE;
import static com.atwoz.member.domain.member.profile.physical_profile.Gender.MALE;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberMissions extends BaseEntity {

    private static final int MAN_DAILY_LIMIT = 2;
    private static final int WOMAN_DAILY_LIMIT = 3;
    private static final Map<Gender, Integer> GENDER_DAILY_LIMIT = Map.of(
            MALE, MAN_DAILY_LIMIT,
            FEMALE, WOMAN_DAILY_LIMIT
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @JoinColumn(name = "member_missions_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberMission> memberMissions = new ArrayList<>();

    public static MemberMissions createWithMemberId(final Long memberId) {
        return MemberMissions.builder()
                .memberId(memberId)
                .memberMissions(new ArrayList<>())
                .build();
    }

    public void addClearedMission(final Gender memberGender, final MemberMission memberMission) {
        validateCanAddMission(memberGender, memberMission.getMission());
        this.memberMissions.add(memberMission);
    }

    private void validateCanAddMission(final Gender memberGender, final Mission mission) {
        validateChallengeMission(mission);
        validateDailyMissionWithGender(memberGender, mission);
    }

    private void validateChallengeMission(final Mission mission) {
        if (!mission.isChallengeMission()) {
            return;
        }

        this.memberMissions.stream()
                .filter(MemberMission::isChallengeMission)
                .filter(memberMission -> memberMission.isSameMission(mission.getId()))
                .findAny()
                .ifPresent(memberMission -> {
                    throw new AlreadyChallengeMissionExistedException();
                });
    }

    private void validateDailyMissionWithGender(final Gender memberGender, final Mission mission) {
        if (mission.isChallengeMission()) {
            return;
        }

        int existedMissionsSize = extractTodayDailyMissionsSize(mission);
        if (existedMissionsSize >= GENDER_DAILY_LIMIT.get(memberGender)) {
            throw new AlreadyDailyMissionExistedLimitException();
        }
    }

    private int extractTodayDailyMissionsSize(final Mission mission) {
        return this.memberMissions.stream()
                .filter(memberMission -> !memberMission.isChallengeMission())
                .filter(memberMission -> memberMission.isSameMission(mission.getId()))
                .filter(memberMission -> memberMission.isSameDayCreated(LocalDateTime.now()))
                .toList()
                .size();
    }

    public Integer receiveRewardBy(final Long missionId) {
        return this.memberMissions.stream()
                .filter(memberMission -> memberMission.isSameMission(missionId))
                .findAny()
                .orElseThrow(MemberMissionNotFoundException::new)
                .receiveReward();
    }
}
