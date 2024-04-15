package com.atwoz.member.domain.member;

import com.atwoz.global.domain.SoftDeleteBaseEntity;
import com.atwoz.member.domain.member.dto.MemberProfileDto;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.domain.member.vo.MemberRole;
import com.atwoz.member.domain.member.vo.MemberStatus;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static com.atwoz.member.domain.member.vo.MemberGrade.SILVER;
import static com.atwoz.member.domain.member.vo.MemberRole.MEMBER;
import static com.atwoz.member.domain.member.vo.MemberStatus.ACTIVE;

@Getter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_at = current_timestamp WHERE id = ?")
@SQLRestriction("deleted_at is null")
@Entity
public class Member extends SoftDeleteBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recommenderId;

    private String nickname;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private MemberProfile memberProfile;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberGrade memberGrade;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    public boolean isAdmin() {
        return this.memberRole.isAdministrator();
    }

    public static Member createWithOAuth(final String phoneNumber) {
        return Member.builder()
                .phoneNumber(phoneNumber)
                .memberProfile(MemberProfile.createWith("남성"))
                .memberGrade(SILVER)
                .memberStatus(ACTIVE)
                .memberRole(MEMBER)
                .build();
    }

    public static Member createWithPass(final String gender,
                                        final String phoneNumber) {
        return Member.builder()
                .phoneNumber(phoneNumber)
                .memberProfile(MemberProfile.createWith(gender))
                .memberGrade(SILVER)
                .memberStatus(ACTIVE)
                .memberRole(MEMBER)
                .build();
    }

    public void initializeWith(final String nickname, final Long recommenderId,
                               final MemberProfileDto memberProfileDto) {
        this.nickname = nickname;
        initializeRecommenderId(recommenderId);
        memberProfile.change(memberProfileDto);
    }

    private void initializeRecommenderId(final Long recommenderId) {
        if (recommenderId != null) {
            this.recommenderId = recommenderId;
        }
    }

    public void updateWith(final String nickname, final MemberProfileDto memberProfileDto) {
        this.nickname = nickname;
        memberProfile.change(memberProfileDto);
    }
}
