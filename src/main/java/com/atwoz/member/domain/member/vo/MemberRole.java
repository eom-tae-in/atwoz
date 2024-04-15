package com.atwoz.member.domain.member.vo;

import com.atwoz.member.exception.exceptions.member.RoleNotFoundException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MemberRole {

    MEMBER("member"),
    ADMIN("admin");

    private final String role;

    MemberRole(final String role) {
        this.role = role;
    }

    public static MemberRole from(final String role) {
        return Arrays.stream(values())
                .filter(value -> value.role.equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(RoleNotFoundException::new);
    }

    public boolean isAdministrator() {
        return this.equals(ADMIN);
    }
}
