package com.atwoz.admin.ui.auth.support;

import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class AdminAuthenticationContext {

    private Long memberId;

    public void setAuthentication(final Long memberId) {
        this.memberId = memberId;
    }

    public Long getPrincipal() {
        if (Objects.isNull(this.memberId)) {
            throw new AdminLoginInvalidException();
        }

        return memberId;
    }
}
