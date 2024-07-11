package com.atwoz.admin.ui.auth.support;

public interface AdminTokenExtractor {

    <T> T extract(String token, String claimName, Class<T> classType);
}
