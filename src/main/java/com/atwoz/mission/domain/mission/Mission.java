package com.atwoz.mission.domain.mission;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.mission.domain.mission.vo.MissionType;
import com.atwoz.mission.domain.mission.vo.PublicOption;
import com.atwoz.mission.exception.mission.exceptions.RewardValueInvalidException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Mission extends BaseEntity {

    private static final int MINIMUM_REWARD_VALUE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer reward;

    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    @Enumerated(EnumType.STRING)
    private PublicOption publicOption;

    private Mission(final String title, final Integer reward, final MissionType missionType, final PublicOption publicOption) {
        this.title = title;
        this.reward = reward;
        this.missionType = missionType;
        this.publicOption = publicOption;
    }

    public static Mission createDefaultRule(final String title, final Integer reward, final String missionType, final String publicOption) {
        validateRewardValue(reward);

        MissionType mission = MissionType.from(missionType);
        PublicOption option = PublicOption.from(publicOption);

        return new Mission(title, reward, mission, option);
    }

    private static void validateRewardValue(final Integer reward) {
        if (reward < MINIMUM_REWARD_VALUE) {
            throw new RewardValueInvalidException();
        }
    }

    public boolean isSameMission(final Long missionId) {
        return this.id.equals(missionId);
    }

    public boolean isChallengeMission() {
        return missionType == MissionType.CHALLENGE;
    }
}
