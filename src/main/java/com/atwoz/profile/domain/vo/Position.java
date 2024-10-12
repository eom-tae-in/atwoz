package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.LatitudeRangeException;
import com.atwoz.profile.exception.exceptions.LongitudeRangeException;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
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
public class Position {

    private static final BigDecimal MIN_LATITUDE = BigDecimal.valueOf(-90);
    private static final BigDecimal MAX_LATITUDE = BigDecimal.valueOf(90);
    private static final BigDecimal MIN_LONGITUDE = BigDecimal.valueOf(-180);
    private static final BigDecimal MAX_LONGITUDE = BigDecimal.valueOf(180);

    private BigDecimal latitude;
    private BigDecimal longitude;

    public static Position createWith(final BigDecimal latitude, final BigDecimal longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);

        return new Position(latitude, longitude);
    }

    private static void validateLatitude(final BigDecimal latitude) {
        if (latitude.compareTo(MIN_LATITUDE) < 0 || latitude.compareTo(MAX_LATITUDE) > 0) {
            throw new LatitudeRangeException();
        }
    }

    private static void validateLongitude(final BigDecimal longitude) {
        if (longitude.compareTo(MIN_LONGITUDE) < 0 || longitude.compareTo(MAX_LONGITUDE) > 0) {
            throw new LongitudeRangeException();
        }
    }
}
