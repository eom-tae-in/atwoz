package com.atwoz.admin.infrastructure.auth;

import com.atwoz.admin.domain.admin.AdminRefreshToken;
import com.atwoz.admin.domain.admin.AdminRefreshTokenRepository;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AdminRedisRefreshTokenRepository implements AdminRefreshTokenRepository {

    @Value("${redis.expiration-period}")
    private int expirationPeriod;

    private final RedisTemplate<String, Long> redisTemplate;


    @Override
    public void save(final AdminRefreshToken adminRefreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(adminRefreshToken.refreshToken(), adminRefreshToken.memberId());
        redisTemplate.expire(adminRefreshToken.refreshToken(), expirationPeriod, TimeUnit.DAYS);
    }

    @Override
    public Optional<AdminRefreshToken> findById(final String refreshToken) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long memberId = valueOperations.get(refreshToken);
        if (Objects.isNull(memberId)) {
            return Optional.empty();
        }

        return Optional.of(new AdminRefreshToken(refreshToken, memberId));
    }

    @Override
    public void delete(final String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}
