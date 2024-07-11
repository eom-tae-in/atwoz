package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.AlertTokenRepository;
import com.atwoz.alert.exception.exceptions.ReceiverTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Repository
public class FirebaseTokenRepository implements AlertTokenRepository {

    private final StringRedisTemplate tokenRedisTemplate;

    @Override
    public void saveToken(final Long id, final String token) {
        tokenRedisTemplate.opsForValue()
                .set(convertId(id), token);
    }

    @Override
    public String getToken(final Long id) {
        validateTokenExistence(id);
        return tokenRedisTemplate.opsForValue()
                .get(convertId(id));
    }

    private void validateTokenExistence(final Long id) {
        if (!hasKey(id)) {
            throw new ReceiverTokenNotFoundException();
        }
    }

    @Override
    public void deleteToken(final Long id) {
        tokenRedisTemplate.delete(convertId(id));
    }

    @Override
    public boolean hasKey(final Long id) {
        return TRUE.equals(tokenRedisTemplate.hasKey(convertId(id)));
    }

    private String convertId(final Long id) {
        return String.valueOf(id);
    }
}
