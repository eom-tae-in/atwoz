package com.atwoz.mission.domain.membermission;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.member.domain.info.profile.body.Gender;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.atwoz.member.domain.info.profile.body.Gender.FEMALE;
import static com.atwoz.member.domain.info.profile.body.Gender.MALE;

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
        validateIsCanAddMission(memberGender, memberMission.getMission());
        this.memberMissions.add(memberMission);
    }

    private void validateIsCanAddMission(final Gender memberGender, final Mission mission) {
        validateChallengeMission(mission);
        validateDailyMissionWithGender(memberGender, mission);
    }

    private void validateChallengeMission(final Mission mission) {
        List<MemberMission> existedMission = extractChallengeMission(mission);

        if (!existedMission.isEmpty()) {
            throw new AlreadyChallengeMissionExistedException();
        }
    }

    private List<MemberMission> extractChallengeMission(final Mission mission) {
        return this.memberMissions.stream()
                .filter(MemberMission::isChallengeMission)
                .filter(memberMission -> memberMission.isSameMission(mission.getId()))
                .toList();
    }

    private void validateDailyMissionWithGender(final Gender memberGender, final Mission mission) {
        List<MemberMission> existedMission = extractTodayDailyMissions(mission);

        if (existedMission.size() >= GENDER_DAILY_LIMIT.get(memberGender)) {
            throw new AlreadyDailyMissionExistedLimitException();
        }
    }

    private List<MemberMission> extractTodayDailyMissions(final Mission mission) {
        return this.memberMissions.stream()
                .filter(memberMission -> !memberMission.isChallengeMission())
                .filter(memberMission -> memberMission.isSameMission(mission.getId()))
                .filter(memberMission -> memberMission.getCreatedAt().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
                .toList();
    }

    public Integer receiveRewardBy(final Long missionId) {
        return this.memberMissions.stream()
                .filter(memberMission -> memberMission.isSameMission(missionId))
                .findAny()
                .orElseThrow(MemberMissionNotFoundException::new)
                .receiveReward();
    }
}
