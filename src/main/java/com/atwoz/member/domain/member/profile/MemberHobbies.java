package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.profile.vo.Hobby;
import com.atwoz.member.exception.exceptions.member.profile.HobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.HobbySizeException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class MemberHobbies {

    private static final int MIN_HOBBY_SIZE = 1;
    private static final int MAX_HOBBY_SIZE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_hobbies_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MemberHobby> hobbies = new HashSet<>();

    public void change(final List<String> hobbyCodes) {
        validateHobbyCodes(hobbyCodes);
        changeHobbies(hobbyCodes);
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

    private void changeHobbies(final List<String> hobbyCodes) {
        hobbies.removeIf(memberHobby -> !memberHobby.hasMatchingHobbyCodeOf(hobbyCodes));
        hobbyCodes.stream()
                .map(MemberHobby::createWith)
                .filter(memberHobby -> !isAlreadyExist(memberHobby))
                .forEach(memberHobby -> hobbies.add(memberHobby));
    }

    private boolean isAlreadyExist(final MemberHobby memberHobby) {
        return hobbies.stream()
                .anyMatch(memberHobby::isSame);
    }
}
