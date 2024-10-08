package com.atwoz.profile.infrastructure;

import com.atwoz.profile.domain.NicknameGenerator;

public class NicknameFakeGenerator implements NicknameGenerator {

    private static final String FAKE_NICKNAME = "nickname";

    @Override
    public String createRandomNickname() {
        return FAKE_NICKNAME;
    }
}
