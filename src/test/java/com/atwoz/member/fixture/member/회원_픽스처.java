package com.atwoz.member.fixture.member;

import com.atwoz.member.domain.member.BlockedMember;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.domain.member.vo.ContactType;
import com.atwoz.member.domain.member.vo.MemberAccountStatus;
import com.atwoz.member.domain.member.vo.MemberGrade;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 회원_픽스처 {

    // TODO: CONTACT 정보는 유일한 값이 들어가야한다. (연락처 정보 + 연락처 타입)이 유일해야한다.

    private static final LocalDate DEFAULT_LATEST_VISIT_DATE = LocalDate.now();
    private static final MemberAccountStatus DEFAULT_MEMBER_ACCOUNT_STATUE = MemberAccountStatus.ACTIVE;
    private static final String DEFAULT_CONTACT_TYPE = ContactType.PHONE_NUMBER.getType();
    private static final String DEFAULT_CONTACT_VALUE = "01012345678";
    private static final MemberGrade DEFAULT_MEMBER_GRADE = MemberGrade.SILVER;
    private static final List<BlockedMember> DEFAULT_BLOCKED_MEMBERS = new ArrayList<>();

    public static Member 회원_생성() {
        return Member.builder()
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .memberAccountStatus(DEFAULT_MEMBER_ACCOUNT_STATUE)
                .contact(Contact.createWith(DEFAULT_CONTACT_TYPE, DEFAULT_CONTACT_VALUE))
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .blockedMembers(DEFAULT_BLOCKED_MEMBERS)
                .build();
    }

    public static Member 회원_생성_회원등급(final MemberGrade memberGrade) {
        return Member.builder()
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .memberAccountStatus(DEFAULT_MEMBER_ACCOUNT_STATUE)
                .contact(Contact.createWith(DEFAULT_CONTACT_TYPE, DEFAULT_CONTACT_VALUE))
                .memberGrade(memberGrade)
                .blockedMembers(DEFAULT_BLOCKED_MEMBERS)
                .build();
    }

    public static Member 회원_생성_전화번호(final String phoneNumber) {
        return Member.builder()
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .memberAccountStatus(DEFAULT_MEMBER_ACCOUNT_STATUE)
                .contact(Contact.createWith(ContactType.PHONE_NUMBER.getType(), phoneNumber))
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .blockedMembers(DEFAULT_BLOCKED_MEMBERS)
                .build();
    }
}
