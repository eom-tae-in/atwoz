package com.atwoz.member.domain.member.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Contact {

    private ContactType contactType;

    private String contactValue;

    public static Contact createWith(final String contactType, final String contactValue) {
        return new Contact(ContactType.findByType(contactType), contactValue);
    }
}
