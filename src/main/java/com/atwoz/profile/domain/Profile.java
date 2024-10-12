package com.atwoz.profile.domain;

import com.atwoz.global.domain.BaseEntity;
import com.atwoz.profile.domain.vo.Drink;
import com.atwoz.profile.domain.vo.Graduate;
import com.atwoz.profile.domain.vo.Location;
import com.atwoz.profile.domain.vo.Mbti;
import com.atwoz.profile.domain.vo.PhysicalProfile;
import com.atwoz.profile.domain.vo.ProfileAccessStatus;
import com.atwoz.profile.domain.vo.Religion;
import com.atwoz.profile.domain.vo.Smoke;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    private Long recommenderId;

    @Column(nullable = false)
    private String nickname;

    // jobcode로 jobCode 간접 참조
    @Column(nullable = false)
    private String jobCode;

    @Enumerated(value = EnumType.STRING)
    private Graduate graduate;

    @Enumerated(value = EnumType.STRING)
    private Smoke smoke;

    @Enumerated(value = EnumType.STRING)
    private Drink drink;

    @Enumerated(value = EnumType.STRING)
    private Religion religion;

    @Enumerated(value = EnumType.STRING)
    private Mbti mbti;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProfileAccessStatus profileAccessStatusByAdmin;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProfileAccessStatus profileAccessStatusByUser;

    // 키, 성별, 나이
    @Embedded
    private PhysicalProfile physicalProfile;

    @Embedded
    private Location location;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserHobbies userHobbies;

    public static Profile createWith(
            final Long memberId,
            final Long recommenderId,
            final String nickname,
            final String jobCode,
            final String graduate,
            final String smoke,
            final String drink,
            final String religion,
            final String mbti,
            final int birthYear,
            final int height,
            final String gender,
            final String city,
            final String sector,
            final List<String> hobbyCodes,
            final YearManager yearManager
    ) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(recommenderId)
                .nickname(nickname)
                .jobCode(jobCode)
                .graduate(Graduate.findByName(graduate))
                .smoke(Smoke.findByName(smoke))
                .drink(Drink.findByName(drink))
                .religion(Religion.findByName(religion))
                .mbti(Mbti.findByName(mbti))
                .profileAccessStatusByAdmin(ProfileAccessStatus.WAITING)
                .profileAccessStatusByUser(ProfileAccessStatus.WAITING)
                .physicalProfile(PhysicalProfile.createWith(birthYear, height, gender, yearManager))
                .location(Location.createWith(city, sector))
                .userHobbies(UserHobbies.createWith(hobbyCodes))
                .build();
    }

    public void update(
            final String nickname,
            final String jobCode,
            final String graduate,
            final String smoke,
            final String drink,
            final String religion,
            final String mbti,
            final int birthYear,
            final int height,
            final String gender,
            final String city,
            final String sector,
            final List<String> hobbyCodes,
            final YearManager yearManager
    ) {
        this.nickname = nickname;
        this.jobCode = jobCode;
        this.graduate = Graduate.findByName(graduate);
        this.drink = Drink.findByName(drink);
        this.smoke = Smoke.findByName(smoke);
        this.religion = Religion.findByName(religion);
        this.mbti = Mbti.findByName(mbti);
        this.physicalProfile = PhysicalProfile.createWith(birthYear, height, gender, yearManager);
        this.location = Location.createWith(city, sector);
        userHobbies.update(hobbyCodes);
    }


    // TODO: 추후에 닉네임 변경 기능만 분리해서 구현할 수도 있다.
}
