package com.atwoz.member.domain.member.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
public class MemberHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "hobby_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hobby hobby;

    public static MemberHobby createWith(final Hobby hobby) {
        return MemberHobby.builder()
                .hobby(hobby)
                .build();
    }

    public boolean isSame(final MemberHobby memberHobby) {
        return this.equals(memberHobby);
    }
}
