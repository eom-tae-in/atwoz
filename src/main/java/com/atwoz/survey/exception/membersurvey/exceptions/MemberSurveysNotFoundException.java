package com.atwoz.survey.exception.membersurvey.exceptions;

public class MemberSurveysNotFoundException extends RuntimeException {

    public MemberSurveysNotFoundException() {
        super("회원에 대한 연애고사 내역이 아예 없습니다.");
    }
}
