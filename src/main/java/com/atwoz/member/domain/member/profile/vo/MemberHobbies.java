package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.HobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.HobbySizeException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberHobbies {

    private static final int MIN_HOBBY_SIZE = 1;
    private static final int MAX_HOBBY_SIZE = 3;

    @ElementCollection
    @CollectionTable(name = "HOBBIES", joinColumns = @JoinColumn(name = "profile_id"))
    @Enumerated(EnumType.STRING)
    private Set<Hobby> hobbies = new HashSet<>();

    public MemberHobbies changeWith(final List<String> hobbyCodes) {
        validateHobbyCodes(hobbyCodes);
        Set<Hobby> uniqueHobbies = convertToUniqueHobbies(hobbyCodes);

        if (isSameAsCurrentValue(uniqueHobbies)) {
            return new MemberHobbies(this.hobbies);
        }

        return new MemberHobbies(uniqueHobbies);
    }

    private void validateHobbyCodes(final List<String> hobbyCodes) {
        validateSize(hobbyCodes);
        validateDuplicates(hobbyCodes);
        validateHasInvalidCodes(hobbyCodes);
    }

    private void validateSize(final List<String> hobbyCodes) {
        int size = hobbyCodes.size();

        if (size < MIN_HOBBY_SIZE || MAX_HOBBY_SIZE < size) {
            throw new HobbySizeException();
        }
    }

    private void validateDuplicates(final List<String> hobbyCodes) {
        HashSet<String> uniqueCodes = new HashSet<>(hobbyCodes);

        if (uniqueCodes.size() != hobbyCodes.size()) {
            throw new HobbyDuplicateException();
        }
    }

    private void validateHasInvalidCodes(final List<String> hobbyCodes) {
        if (hasInvalidHobbyCode(hobbyCodes)) {
            throw new InvalidHobbyException();
        }
    }

    private boolean hasInvalidHobbyCode(final List<String> hobbyCodes) {
        return hobbyCodes.stream()
                .anyMatch(hobbyCode -> !Hobby.isValidCode(hobbyCode));
    }

    private Set<Hobby> convertToUniqueHobbies(final List<String> hobbyCodes) {
        return hobbyCodes.stream()
                .map(Hobby::findByCode)
                .collect(Collectors.toSet());
    }

    private boolean isSameAsCurrentValue(final Set<Hobby> uniqueHobbies) {
        return uniqueHobbies.equals(this.hobbies);
    }
}
