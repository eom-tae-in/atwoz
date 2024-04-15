package com.atwoz.member.domain.member.vo;

public enum MemberGrade {
    DIAMOND("다이아"),
    GOLD("골드"),
    SILVER("실버");

    private final String grade;

    MemberGrade(final String grade) {
        this.grade = grade;
    }
}
