package com.atwoz.member.domain.member;

import com.atwoz.global.domain.SoftDeleteBaseEntity;
import com.atwoz.member.domain.member.dto.initial.InternalProfileInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalProfileUpdateRequest;
import com.atwoz.member.domain.member.vo.MemberGrade;
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
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static com.atwoz.member.domain.member.vo.MemberGrade.SILVER;
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
    private MemberGrade memberGrade;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    private LocalDate latestVisitDate;

    public static Member createWithOAuth(final String phoneNumber) {
        return Member.builder()
                .phoneNumber(phoneNumber)
                .memberProfile(MemberProfile.createWith("남성"))
                .memberGrade(SILVER)
                .memberStatus(ACTIVE)
                .latestVisitDate(LocalDate.now())
                .build();
    }

    public static Member createWithPass(final String gender,
                                        final String phoneNumber) {
        return Member.builder()
                .phoneNumber(phoneNumber)
                .memberProfile(MemberProfile.createWith(gender))
                .memberGrade(SILVER)
                .memberStatus(ACTIVE)
                .build();
    }

    public void initializeWith(final String nickname,
                               final Long recommenderId,
                               final InternalProfileInitializeRequest internalProfileInitializeRequest) {
        this.nickname = nickname;
        initializeRecommenderId(recommenderId);
        memberProfile.initialize(internalProfileInitializeRequest);
    }

    private void initializeRecommenderId(final Long recommenderId) {
        if (recommenderId != null) {
            this.recommenderId = recommenderId;
        }
    }

    public void updateWith(final String nickname,
                           final InternalProfileUpdateRequest internalProfileUpdateRequest) {
        this.nickname = nickname;
        memberProfile.update(internalProfileUpdateRequest);
    }

    public void updateVisitStatus() {
        latestVisitDate = LocalDate.now();
    }
}
