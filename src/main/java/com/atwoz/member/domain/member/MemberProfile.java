package com.atwoz.member.domain.member;

import com.atwoz.member.domain.member.dto.MemberProfileInfo;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.ProfileAccessStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private Profile profile;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ProfileAccessStatus profileAccessStatus;

    public static MemberProfile createWith(final String gender) {
        return MemberProfile.builder()
                .profile(Profile.createWith(gender))
                .profileAccessStatus(ProfileAccessStatus.WAITING)
                .build();
    }

    public void change(final MemberProfileInfo memberProfileInfo) {
        profile.change(memberProfileInfo);
    }

    public void changeProfileAccessStatus(final String status) {
        this.profileAccessStatus = ProfileAccessStatus.findBy(status);
    }
}
