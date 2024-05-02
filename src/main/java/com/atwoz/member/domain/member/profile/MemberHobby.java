package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.profile.vo.Hobby;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    public static MemberHobby createWith(final String hobbyCode) {
        return MemberHobby.builder()
                .hobby(Hobby.findByCode(hobbyCode))
                .build();
    }

    public boolean hasMatchingHobbyCodeOf(final List<String> hobbyCodes) {
        return hobbyCodes.contains(hobby.getCode());
    }

    public boolean isSame(final MemberHobby memberHobby) {
        return this.hobby.equals(memberHobby.hobby);
    }
}
