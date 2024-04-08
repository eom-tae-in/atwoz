package com.atwoz.member.infrastructure.member.physical;

import com.atwoz.member.domain.member.profile.physical.YearManager;

public class FakeYearManager implements YearManager {

    private static final int YEAR = 2024;

    @Override
    public int getCurrentYear() {
        return YEAR;
    }
}
