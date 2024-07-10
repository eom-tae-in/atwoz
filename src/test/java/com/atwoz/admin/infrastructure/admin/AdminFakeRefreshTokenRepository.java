package com.atwoz.admin.infrastructure.admin;

import com.atwoz.admin.domain.admin.AdminRefreshToken;
import com.atwoz.admin.domain.admin.AdminRefreshTokenRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdminFakeRefreshTokenRepository implements AdminRefreshTokenRepository {

    private final Map<AdminRefreshToken, Long> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public void save(final AdminRefreshToken adminRefreshToken) {
        map.put(adminRefreshToken, id++);
    }

    @Override
    public Optional<AdminRefreshToken> findById(final String refreshToken) {
        return map.keySet().stream()
                .filter(adminRefreshToken -> refreshToken.equals(adminRefreshToken.refreshToken()))
                .findAny();
    }

    @Override
    public void delete(final String refreshToken) {
        map.keySet()
                .removeIf(adminRefreshToken -> refreshToken.equals(adminRefreshToken.refreshToken()));
    }
}
