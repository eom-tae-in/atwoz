package com.atwoz.admin.ui.auth.support;

import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class AdminAuthenticationContext {

    private static final Long ANONYMOUS_ADMIN = -1L;

    private Long adminId;

    public void setAuthentication(final Long adminId) {
        this.adminId = adminId;
    }

    public Long getPrincipal() {
        if (Objects.isNull(this.adminId)) {
            throw new AdminLoginInvalidException();
        }

        return adminId;
    }

    public void setAnonymous() {
        this.adminId = ANONYMOUS_ADMIN;
    }
}
