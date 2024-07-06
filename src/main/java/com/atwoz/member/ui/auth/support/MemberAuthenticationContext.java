package com.atwoz.member.ui.auth.support;

import com.atwoz.member.exception.exceptions.auth.MemberLoginInvalidException;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Component
public class MemberAuthenticationContext {

    private Long memberId;

    public void setAuthentication(final Long memberId) {
        this.memberId = memberId;
    }

    public Long getPrincipal() {
        if (Objects.isNull(this.memberId)) {
            throw new MemberLoginInvalidException();
        }

        return memberId;
    }
}
