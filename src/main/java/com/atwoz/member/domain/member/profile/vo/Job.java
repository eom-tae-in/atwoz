package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.InvalidJobException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Job {

    RESEARCH_AND_DEVELOP("연구개발/엔지니어", "A001"),
    SELF_BUSINESS("개인사업/자영업", "A002"),
    SALES("영업/판매", "A003"),
    PLAN("경영/기획", "A004"),
    STUDY_FOR_FUTURE("미래를 위한 공부중", "A005"),
    PREPARE_FOR_CAREER("취업을 위한 준비중", "A006"),
    EDUCATION("교육", "A007"),
    ART_AND_PHYSIC("예술/체육", "A008"),
    FOOD("요식업", "A009"),
    MEDICAL("의료/보건", "A010"),
    MECHANIC("기계/건설", "A011"),
    DESIGN("디자인", "A012"),
    MARKETING("마케팅/광고", "A013"),
    TRADE("무역/유통", "A014"),
    BROADCAST("방송언론/연예", "A015"),
    LAW("법률/공공", "A016"),
    PRODUCE("생산/제조", "A017"),
    SERVICE("서비스", "A018"),
    TRAVEL("여행/운송", "A019"),
    ETC("기타", "A020");

    private final String name;
    private final String code;

    Job(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public static Job findByCode(final String code) {
        return Arrays.stream(values())
                .filter(job -> job.isSameCode(code))
                .findFirst()
                .orElseThrow(InvalidJobException::new);
    }

    private boolean isSameCode(final String code) {
        return this.code.equals(code);
    }
}
