package com.atwoz.survey.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean required;

    private Survey(final String name, final Boolean required) {
        this.name = name;
        this.required = required;
    }

    public static Survey createWith(final String name, final Boolean required) {
        return new Survey(name, required);
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public void updateRequired(final Boolean required) {
        this.required = required;
    }
}
