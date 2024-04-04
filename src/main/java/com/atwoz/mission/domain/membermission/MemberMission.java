package com.atwoz.mission.domain.membermission;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberMission extends BaseEntity {

    private static final boolean DEFAULT_STATUS = false;
    private static final boolean CLEAR_STATUS = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Mission mission;

    private boolean doesGetReward;

    private MemberMission(final Mission mission) {
        this.mission = mission;
        this.doesGetReward = DEFAULT_STATUS;
    }

    public static MemberMission createDefault(final Mission mission) {
        return new MemberMission(mission);
    }

    public boolean isChallengeMission() {
        return this.mission.isChallengeMission();
    }

    public boolean isSameMission(final Long missionId) {
        return this.mission.isSameMission(missionId);
    }

    public Integer receiveReward() {
        validateCanReceiveReward();

        this.doesGetReward = CLEAR_STATUS;
        return this.mission.getReward();
    }

    private void validateCanReceiveReward() {
        if (doesGetReward) {
            throw new MemberMissionAlreadyRewardedException();
        }
    }
}
