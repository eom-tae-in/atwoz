package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.dto.MemberProfileDto;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private MemberHobbies memberHobbies;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private MemberStyles memberStyles;

    @Embedded
    private Location location;

    public static Profile createWith(final String gender) {
        return Profile.builder()
                .physicalProfile(PhysicalProfile.createWith(gender))
                .memberHobbies(new MemberHobbies())
                .memberStyles(new MemberStyles())
                .build();
    }

    public void change(final MemberProfileDto memberProfileDto) {
        this.job = Job.findByCode(memberProfileDto.job());
        this.graduate = Graduate.findByName(memberProfileDto.graduate());
        this.smoke = Smoke.findByName(memberProfileDto.smoke());
        this.drink = Drink.findByName(memberProfileDto.drink());
        this.religion = Religion.findByName(memberProfileDto.religion());
        this.mbti = Mbti.findByName(memberProfileDto.mbti());
        this.physicalProfile.change(memberProfileDto.physicalProfileDto());
        this.location = new Location(memberProfileDto.city(), memberProfileDto.sector());
        this.memberHobbies.change(memberProfileDto.getHobbies());
        this.memberStyles.change(memberProfileDto.getStyles());
    }
}
