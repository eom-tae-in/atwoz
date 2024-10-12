package com.atwoz.profile.domain;

import com.atwoz.profile.exception.exceptions.DuplicatedUserHobbyException;
import com.atwoz.profile.exception.exceptions.UserHobbySizeException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class UserHobbies {

    private static final int MIN_HOBBY_SIZE = 0;
    private static final int MAX_HOBBY_SIZE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_hobbies_id")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserHobby> hobbies = new ArrayList<>();

    public static UserHobbies createWith(final List<String> hobbyCodes) {
        validateHobbyCodes(hobbyCodes);
        List<UserHobby> userHobbies = hobbyCodes.stream()
                .map(UserHobby::createWith)
                .toList();

        return UserHobbies.builder()
                .hobbies(userHobbies)
                .build();
    }

    private static void validateHobbyCodes(final List<String> hobbyCodes) {
        validateSize(hobbyCodes);
        validateDuplicates(hobbyCodes);
    }

    private static void validateSize(final List<String> hobbyCodes) {
        int size = hobbyCodes.size();

        if (size < MIN_HOBBY_SIZE || MAX_HOBBY_SIZE < size) {
            throw new UserHobbySizeException();
        }
    }

    private static void validateDuplicates(final List<String> hobbyCodes) {
        Set<String> distinctHobbyCodes = new HashSet<>(hobbyCodes);

        if (hobbyCodes.size() != distinctHobbyCodes.size()) {
            throw new DuplicatedUserHobbyException();
        }
    }

    public void update(final List<String> hobbyCodes) {
        validateHobbyCodes(hobbyCodes);
        List<UserHobby> userHobbies = hobbyCodes.stream()
                .map(UserHobby::createWith)
                .toList();

        hobbies.clear();
        hobbies.addAll(userHobbies);
    }
}
