package com.atwoz.member.ui.auth.support;

import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {

    private final Map<OAuthPlatform, OAuthProviderRequest> properties;

    public OAuthProviderRequest findByProviderName(final String name) {
        return properties.get(OAuthPlatform.findPlatform(name));
    }
}
