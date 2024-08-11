package com.atwoz.member.domain.member.profile.physical;

import com.atwoz.member.domain.member.dto.initial.InternalPhysicalProfileInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalPhysicalProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.exception.exceptions.member.profile.physical.AgeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.HeightRangeException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PhysicalProfile {

    private static final int MIN_AGE = 19;
    private static final int MAX_AGE = 45;
    private static final int MIN_HEIGHT = 140;
    private static final int MAX_HEIGHT = 199;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    private int height;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    public static PhysicalProfile createWith(final String gender) {
        return PhysicalProfile.builder()
                .gender(Gender.findByName(gender))
                .build();
    }

    public void initialize(final InternalPhysicalProfileInitializeRequest request) {
        changeAge(request.birthYear(), request.yearManager());
        changeHeight(request.height());
    }

    public void update(final InternalPhysicalProfileUpdateRequest request) {
        changeAge(request.birthYear(), request.yearManager());
        changeHeight(request.height());
    }

    private void changeAge(final int birthYear, final YearManager yearManager) {
        int memberAge = calculateMemberAge(birthYear, yearManager);
        validateAge(memberAge);
        this.age = memberAge;
    }

    private int calculateMemberAge(final int birthYear, final YearManager yearManager) {
        int currentYear = yearManager.getCurrentYear();
        return currentYear - birthYear;
    }

    private void validateAge(final int memberAge) {
        if (memberAge < MIN_AGE || MAX_AGE < memberAge) {
            throw new AgeRangeException();
        }
    }

    private void changeHeight(final int height) {
        validateHeight(height);
        this.height = height;
    }

    private void validateHeight(final int height) {
        if (height < MIN_HEIGHT || MAX_HEIGHT < height) {
            throw new HeightRangeException();
        }
    }
}
