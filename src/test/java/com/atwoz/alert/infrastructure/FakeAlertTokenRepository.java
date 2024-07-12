package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.AlertTokenRepository;
import com.atwoz.alert.exception.exceptions.ReceiverTokenNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class FakeAlertTokenRepository implements AlertTokenRepository {

    private final Map<Long, String> map = new HashMap<>();

    @Override
    public void saveToken(final Long id, final String token) {
        map.put(id, token);
    }

    @Override
    public String getToken(final Long id) {
        validateTokenExistence(id);
        return map.get(id);
    }

    private void validateTokenExistence(final Long id) {
        if (!map.containsKey(id)) {
            throw new ReceiverTokenNotFoundException();
        }
    }

    @Override
    public void deleteToken(final Long id) {
        map.remove(id);
    }

    @Override
    public boolean hasKey(final Long id) {
        return map.containsKey(id);
    }
}
