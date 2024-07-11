package com.atwoz.admin.domain.admin;

import com.atwoz.admin.domain.admin.vo.AdminStatus;
import com.atwoz.admin.domain.admin.vo.Authority;
import com.atwoz.admin.domain.admin.vo.Department;
import com.atwoz.admin.exception.exceptions.InvalidPasswordException;
import com.atwoz.admin.exception.exceptions.PasswordMismatchException;
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
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus adminStatus;

    public static Admin createWith(final String email,
                                   final String password,
                                   final String confirmPassword,
                                   final String name,
                                   final String phoneNumber) {
        validatePasswordsMatch(password, confirmPassword);
        return Admin.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .authority(Authority.ADMIN)
                .department(Department.OPERATION)
                .adminStatus(AdminStatus.PENDING)
                .build();
    }

    private static void validatePasswordsMatch(final String password,
                                              final String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException();
        }
    }

    public void validatePassword(final String password) {
        if (!password.equals(this.password)) {
            throw new InvalidPasswordException();
        }
    }
}
