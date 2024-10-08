package com.atwoz.profile.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class UserHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    hobby code로 hobby 간접참조
     */
    @Column(nullable = false)
    private String hobbyCode;

    public static UserHobby createWith(final String hobbyCode) {
        return UserHobby.builder()
                .hobbyCode(hobbyCode)
                .build();
    }

    public boolean isSameCode(final UserHobby userHobby) {
        return hobbyCode.equals(userHobby.hobbyCode);
    }
}
