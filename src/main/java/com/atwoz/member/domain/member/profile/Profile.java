package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.dto.MemberProfileInfo;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Job job;

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

    // 키, 성별, 나이
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, orphanRemoval = true)
    private PhysicalProfile physicalProfile;

    @Embedded
    private Location location;

    @Embedded
    private MemberHobbies memberHobbies;

    @Embedded
    private MemberStyles memberStyles;

    public static Profile createWith(final String gender) {
        return Profile.builder()
                .physicalProfile(PhysicalProfile.createWith(gender))
                .memberHobbies(new MemberHobbies())
                .memberStyles(new MemberStyles())
                .build();
    }

    public void change(final MemberProfileInfo memberProfileInfo) {
        this.job = Job.findByCode(memberProfileInfo.job());
        this.graduate = Graduate.findByName(memberProfileInfo.graduate());
        this.smoke = Smoke.findByName(memberProfileInfo.smoke());
        this.drink = Drink.findByName(memberProfileInfo.drink());
        this.religion = Religion.findByName(memberProfileInfo.religion());
        this.mbti = Mbti.findByName(memberProfileInfo.mbti());
        physicalProfile.change(memberProfileInfo.physicalProfileInfo());
        this.location = new Location(memberProfileInfo.city(), memberProfileInfo.sector());
        this.memberHobbies = memberHobbies.changeWith(memberProfileInfo.getHobbies());
        this.memberStyles = memberStyles.changeWith(memberProfileInfo.getStyles());
    }
}
