package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.dto.initial.InternalProfileInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
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

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProfileAccessStatus profileAccessStatus;

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
                .profileAccessStatus(ProfileAccessStatus.WAITING)
                .memberHobbies(new MemberHobbies())
                .memberStyles(new MemberStyles())
                .build();
    }

    public void initialize(final InternalProfileInitializeRequest request) {
        changeProfileFields(
                request.job(),
                request.graduate(),
                request.smoke(),
                request.drink(),
                request.religion(),
                request.mbti(),
                request.city(),
                request.sector()
        );
        this.physicalProfile.initialize(request.internalPhysicalProfileInitializeRequest());
        this.memberHobbies.initialize(request.internalHobbiesInitializeRequest());
        this.memberStyles.initialize(request.internalStylesInitializeRequest());
    }

    public void update(final InternalProfileUpdateRequest request) {
        changeProfileFields(
                request.job(),
                request.graduate(),
                request.smoke(),
                request.drink(),
                request.religion(),
                request.mbti(),
                request.city(),
                request.sector()
        );
        this.physicalProfile.update(request.internalPhysicalProfileUpdateRequest());
        this.memberHobbies.update(request.internalHobbiesUpdateRequest());
        this.memberStyles.update(request.internalStylesUpdateRequest());
    }

    private void changeProfileFields(final String job,
                                     final String graduate,
                                     final String smoke,
                                     final String drink,
                                     final String religion,
                                     final String mbti,
                                     final String city,
                                     final String sector) {
        this.job = Job.findByCode(job);
        this.graduate = Graduate.findByName(graduate);
        this.smoke = Smoke.findByName(smoke);
        this.drink = Drink.findByName(drink);
        this.religion = Religion.findByName(religion);
        this.mbti = Mbti.findByName(mbti);
        this.location = new Location(city, sector);
    }
}
