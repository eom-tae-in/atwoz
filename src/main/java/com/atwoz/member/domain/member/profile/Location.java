package com.atwoz.member.domain.member.profile;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Location {

    private String city;

    private String sector;

    public Location(final String city, final String sector) {
        this.city = city;
        this.sector = sector;
    }
}
