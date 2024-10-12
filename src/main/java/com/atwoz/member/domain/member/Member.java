package com.atwoz.member.domain.member;

import com.atwoz.global.domain.SoftDeleteBaseEntity;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.domain.member.vo.ContactType;
import com.atwoz.member.domain.member.vo.MemberAccountStatus;
import com.atwoz.member.domain.member.vo.MemberGrade;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import static com.atwoz.member.domain.member.vo.MemberAccountStatus.ACTIVE;
import static com.atwoz.member.domain.member.vo.MemberGrade.SILVER;

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

    private LocalDate latestVisitDate;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberGrade memberGrade;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberAccountStatus memberAccountStatus;

    @Embedded
    private Contact contact;

    @Embedded
    private MemberPushNotifications memberPushNotifications;

    @JoinColumn(name = "member_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BlockedMember> blockedMembers = new ArrayList<>();

    public static Member createWithOAuth(final String phoneNumber) {
        return Member.builder()
                .latestVisitDate(LocalDate.now())
                .memberGrade(SILVER)
                .memberAccountStatus(ACTIVE)
                .contact(Contact.createWith(ContactType.PHONE_NUMBER.getType(), phoneNumber))
                .memberPushNotifications(MemberPushNotifications.createWith())
                .build();
    }

//    public static Member createWithPass(final String gender, final String contactValue) {
//        return Member.builder()
//                .contactValue(contactValue)
//                .memberGrade(SILVER)
//                .memberAccountStatus(ACTIVE)
//                .build();
//    }

    // TODO: 회원 가입 및 로그인 진행 시 해당 메서드를 추가하여 최근 방문일을 갱신해줘야 한다.
    public void updateLastVisitDate() {
        latestVisitDate = LocalDate.now();
    }

    public void updatePushNotifications(
            final boolean isLikeReceivedNotificationOn,
            final boolean isNewMessageNotificationOn,
            final boolean isProfileExchangeNotificationOn,
            final boolean isProfileImageChangeNotificationOn,
            final boolean isLongTimeLoLoginNotificationOn,
            final boolean isInterviewWritingRequestNotificationOn
    ) {
        memberPushNotifications.update(
                isLikeReceivedNotificationOn,
                isNewMessageNotificationOn,
                isProfileExchangeNotificationOn,
                isProfileImageChangeNotificationOn,
                isLongTimeLoLoginNotificationOn,
                isInterviewWritingRequestNotificationOn
        );
    }

    public void updateAccountStatus(final String status) {
        memberAccountStatus = MemberAccountStatus.findByStatus(status);
    }

    public void updateContact(final String contactType, final String contactValue) {
        this.contact = Contact.createWith(contactType, contactValue);
    }
}
