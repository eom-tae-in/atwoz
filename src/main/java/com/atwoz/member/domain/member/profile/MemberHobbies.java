package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.dto.initial.InternalHobbiesInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalHobbiesUpdateRequest;
import com.atwoz.member.exception.exceptions.member.profile.hobby.MemberHobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.MemberHobbySizeException;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public void initialize(final InternalHobbiesInitializeRequest request) {
        List<MemberHobby> requestedMemberHobbies = request.memberHobbies();
        validateHobbyCodes(requestedMemberHobbies);
        changeHobbies(requestedMemberHobbies);
    }

    public void update(final InternalHobbiesUpdateRequest request) {
        List<MemberHobby> requestedHobbies = request.hobbies();
        validateHobbyCodes(requestedHobbies);
        changeHobbies(requestedHobbies);
    }

    private void validateHobbyCodes(final List<MemberHobby> memberHobbies) {
        validateSize(memberHobbies);
        validateDuplicates(memberHobbies);
    }

    private void validateSize(final List<MemberHobby> memberHobbies) {
        int size = memberHobbies.size();

        if (size < MIN_HOBBY_SIZE || MAX_HOBBY_SIZE < size) {
            throw new MemberHobbySizeException();
        }
    }

    private void validateDuplicates(final List<MemberHobby> memberHobbies) {
        Set<String> uniqueHobbyKeys = new HashSet<>();

        for (MemberHobby memberHobby : memberHobbies) {
            Hobby hobby = memberHobby.getHobby();
            String key = hobby.getName() + hobby.getCode();
            if (!uniqueHobbyKeys.add(key)) {
                throw new MemberHobbyDuplicateException();
            }
        }
    }

    private void changeHobbies(final List<MemberHobby> requestedMemberHobbies) {
        hobbies.removeIf(memberHobby -> !requestedMemberHobbies.contains(memberHobby));
        requestedMemberHobbies.stream()
                .filter(this::isNewHobby)
                .forEach(requestedMemberHobby -> hobbies.add(requestedMemberHobby));
    }

    private boolean isNewHobby(final MemberHobby requestedMemberHobby) {
        return hobbies.stream()
                .noneMatch(memberHobby -> memberHobby.isSame(requestedMemberHobby));
    }
}
