package com.atwoz.member.domain.member.vo;

import com.atwoz.member.exception.exceptions.member.InvalidContactTypeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ContactType {

    PHONE_NUMBER("휴대폰 번호"),
    KAKAO_TALK_ID("카카오톡 아이디");

    private final String type;

    ContactType(final String type) {
        this.type = type;
    }

    public static ContactType findByType(final String type) {
        return Arrays.stream(values())
                .filter(contactType -> type.equals(contactType.getType()))
                .findFirst()
                .orElseThrow(InvalidContactTypeException::new);
    }
}
