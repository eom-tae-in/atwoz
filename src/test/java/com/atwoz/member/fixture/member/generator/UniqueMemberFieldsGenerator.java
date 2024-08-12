package com.atwoz.member.fixture.member.generator;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class UniqueMemberFieldsGenerator {

    private static final String PHONE_NUMBER_PREFIX = "010";
    private static final String PHONE_NUMBER_DIGIT_FORMAT = "%08d";
    private static final String NICKNAME_PREFIX = "nickname";
    private final AtomicLong PHONE_NUMBER_SUFFIX = new AtomicLong(0);
    private final AtomicInteger NICKNAME_SUFFIX = new AtomicInteger(0);

    public String generateNickname() {
        return NICKNAME_PREFIX + NICKNAME_SUFFIX.incrementAndGet();
    }

    public String generatePhoneNumber() {
        long suffix = PHONE_NUMBER_SUFFIX.incrementAndGet();
        return PHONE_NUMBER_PREFIX + String.format(PHONE_NUMBER_DIGIT_FORMAT, suffix);
    }
}
