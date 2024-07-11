package com.atwoz.admin.domain.admin;

import java.util.Optional;

public interface AdminRefreshTokenRepository {

    void save(AdminRefreshToken adminRefreshToken);

    Optional<AdminRefreshToken> findById(String refreshToken);

    void delete(String refreshToken);
}
