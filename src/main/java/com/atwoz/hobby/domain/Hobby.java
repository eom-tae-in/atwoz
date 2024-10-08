package com.atwoz.hobby.domain;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    private Hobby(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public static Hobby createWith(final String name, final String code) {
        return new Hobby(name, code);
    }

    public void update(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public boolean isSame(final Hobby hobby) {
        return hobby.equals(this);
    }
}
