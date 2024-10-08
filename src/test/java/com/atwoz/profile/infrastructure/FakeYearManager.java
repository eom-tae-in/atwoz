package com.atwoz.profile.infrastructure;

import com.atwoz.profile.domain.YearManager;

public class FakeYearManager implements YearManager {

    private static final int YEAR = 2024;

    @Override
    public int getCurrentYear() {
        return YEAR;
    }
}
