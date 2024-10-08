package com.atwoz.profile.domain.vo;

import com.atwoz.profile.domain.YearManager;
import com.atwoz.profile.exception.exceptions.AgeRangeException;
import com.atwoz.profile.exception.exceptions.HeightRangeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PhysicalProfile {

    private static final int MIN_AGE = 19;
    private static final int MAX_AGE = 45;
    private static final int MIN_HEIGHT = 140;
    private static final int MAX_HEIGHT = 199;

    private int age;

    private int height;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public static PhysicalProfile createWith(
            final int birthYear,
            final int height,
            final String gender,
            final YearManager yearManager
    ) {
        int age = calculateMemberAge(birthYear, yearManager);
        validateAge(age);
        validateHeight(height);

        return new PhysicalProfile(age, height, Gender.findByName(gender));
    }

    private static int calculateMemberAge(final int birthYear, final YearManager yearManager) {
        int currentYear = yearManager.getCurrentYear();
        return currentYear - birthYear;
    }

    private static void validateAge(final int memberAge) {
        if (memberAge < MIN_AGE || MAX_AGE < memberAge) {
            throw new AgeRangeException();
        }
    }

    private static void validateHeight(final int height) {
        if (height < MIN_HEIGHT || MAX_HEIGHT < height) {
            throw new HeightRangeException();
        }
    }
}
